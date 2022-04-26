package org.dante.springboot.genoffice.flyingsaucer;

import java.io.File;

import org.springframework.util.FileCopyUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class PDFBuilder extends PdfPageEventHelper {

	/**
	 * 页眉
	 */
	public String header = "";

	/**
	 * 文档字体大小，页脚页眉最好和文本大小一致
	 */
	public int presentFontSize = 10;

	/**
	 * 文档页面大小，最好前面传入，否则默认为A4纸张
	 */
	public Rectangle pageSize = PageSize.A4;

	/**
	 * 模板
	 */
	public PdfTemplate total;

	/**
	 * 基础字体对象
	 */
	public BaseFont bf = null;

	/**
	 * 利用基础字体生成的字体对象，一般用于生成中文文字
	 */
	public Font fontDetail = null;
	/**
	 * 水印文件
	 */
	private File watermark = null;
	/**
	 * 是否显示页脚页码信息
	 */
	private boolean isHeaderFooter = false;

	/**
	 * 
	 * @param header
	 * @param presentFontSize
	 * @param pageSize
	 * @param watermark
	 */
	public PDFBuilder(String header, int presentFontSize, Rectangle pageSize, File watermark, boolean isHeaderFooter) {
		this.header = header;
		this.presentFontSize = presentFontSize;
		this.pageSize = pageSize;
		this.watermark = watermark;
		this.isHeaderFooter = isHeaderFooter;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public void setPresentFontSize(int presentFontSize) {
		this.presentFontSize = presentFontSize;
	}

	/**
	 * 文档打开时创建模板
	 */
	public void onOpenDocument(PdfWriter writer, Document document) {
		/**
		 * 共 页 的矩形的长宽高
		 */
		total = writer.getDirectContent().createTemplate(50, 50);
	}

	/**
	 * 关闭每页的时候添加页眉页脚和水印
	 */
	public void onEndPage(PdfWriter writer, Document document) {
		/**
		 * 添加分页
		 */
		if (isHeaderFooter) {
			this.addPage(writer, document);
		}
		/**
		 * 添加水印
		 */
		if (watermark != null) {
			this.addWatermark(writer);
		}
	}

	/**
	 * 
	 * @描述：分页
	 *
	 * @返回：void
	 *
	 * @作者：zhongjy
	 *
	 * @时间：2019年7月15日 下午9:09:39
	 */
	public void addPage(PdfWriter writer, Document document) {
		try {
			if (bf == null) {
				bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
			}
			if (fontDetail == null) {
				/**
				 * 数据体字体
				 */
				fontDetail = new Font(bf, presentFontSize, Font.NORMAL);
				fontDetail.setColor(BaseColor.GRAY);
			}
		} catch (Exception e) {
			log.error("", e);
		}

		/**
		 * 写入页眉
		 */
		ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase(header, fontDetail),
				document.left(), document.top() + 20, 0);
		/**
		 * 写入页脚（分页信息）
		 */
		int pageS = writer.getPageNumber();
		String foot1 = "第 " + pageS + " 页 / 共";
		Phrase footer = new Phrase(foot1, fontDetail);

		/**
		 * 计算前半部分的foot1的长度，后面好定位最后一部分的'Y页'这俩字的x轴坐标，字体长度也要计算进去 = len
		 */
		float len = bf.getWidthPoint(foot1, presentFontSize);

		/**
		 * 拿到当前的PdfContentByte
		 */
		PdfContentByte cb = writer.getDirectContent();

		/**
		 * 写入页脚1，x轴就是(右margin+左margin + right() -left()- len)/2.0F
		 * 再给偏移20F适合人类视觉感受，否则肉眼看上去就太偏左了
		 * y轴就是底边界-20,否则就贴边重叠到数据体里了就不是页脚了；注意Y轴是从下往上累加的，最上方的Top值是大于Bottom好几百开外的
		 */
		ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer,
				(document.rightMargin() + document.right() + document.leftMargin() - document.left() - len) / 2.0F
						+ 20F,
				document.bottom() - 25, 0);

		/**
		 * 写入页脚2的模板（就是页脚的Y页这俩字）添加到文档中，计算模板的和Y轴,X=(右边界-左边界 - 前半部分的len值)/2.0F + len ， y
		 * 轴和之前的保持一致，底边界-20
		 */
		cb.addTemplate(total,
				(document.rightMargin() + document.right() + document.leftMargin() - document.left()) / 2.0F + 20F,
				document.bottom() - 25);
	}

	/**
	 * 
	 * @描述：添加水印
	 *
	 * @返回：void
	 *
	 * @作者：zhongjy
	 *
	 * @时间：2019年7月15日 下午9:12:40
	 */
	public void addWatermark(PdfWriter writer) {
		/**
		 * 水印图片
		 */
		Image image = null;
		try {
			image = Image.getInstance(FileCopyUtils.copyToByteArray(watermark));
			PdfContentByte content = writer.getDirectContentUnder();
			content.beginText();
			/**
			 * 开始写入水印
			 */
			image.setAbsolutePosition(300, 300);
			content.addImage(image);
			content.endText();
		} catch (Exception e) {
			log.error("", e);
		}
	}

	/**
	 * 关闭文档时，替换模板，完成整个页眉页脚组件
	 */
	public void onCloseDocument(PdfWriter writer, Document document) {
		if (isHeaderFooter) {
			/**
			 * 最后一步了，就是关闭文档的时候，将模板替换成实际的 Y 值,至此，page x of y 制作完毕，完美兼容各种文档size
			 */
			total.beginText();
			/**
			 * 生成的模版的字体、颜色
			 */
			total.setFontAndSize(bf, presentFontSize);
			total.setColorFill(BaseColor.GRAY);
			String foot2 = " " + (writer.getPageNumber()) + " 页";
			/**
			 * 模版显示的内容
			 */
			total.showText(foot2);
			total.endText();
			total.closePath();
		}
	}

}
