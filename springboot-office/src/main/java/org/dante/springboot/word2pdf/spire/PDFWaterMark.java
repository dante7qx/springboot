package org.dante.springboot.word2pdf.spire;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.dante.springboot.word2pdf.Consts;
import org.dante.springboot.word2pdf.aspose.PdfWaterMarkUtil;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.PdfBrushes;
import com.spire.pdf.graphics.PdfFont;
import com.spire.pdf.graphics.PdfFontFamily;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.graphics.PdfStringFormat;
import com.spire.pdf.graphics.PdfTextAlignment;
import com.spire.pdf.graphics.PdfTilingBrush;
import com.spire.pdf.widget.PdfPageCollection;

public class PDFWaterMark {

	public static void main(String[] args) throws Exception {
		// 创建PdfDocument对象
		PdfDocument pdf = new PdfDocument();
		// 加载示例文档
		pdf.loadFromFile(Consts.WORD_DIR + "spire/测试.pdf");

//        addTextWatermark(pdf);
        addImageWatermark(pdf);
//		addMultiImageWatermark(pdf);
	}

	/**
	 * 添加文字水印
	 * 
	 * @param pdf
	 */
	static void addTextWatermark(PdfDocument pdf) {
		PdfPageCollection pages = pdf.getPages();
		for (Object pageObj : pages) {
			PdfPageBase page = (PdfPageBase) pageObj;
			insertWatermark(page, "内部保密资料");
		}
		// 保存文档
		pdf.saveToFile("测试水印.pdf");
		pdf.close();
	}

	static void insertWatermark(PdfPageBase page, String watermark) {
		Dimension2D dimension2D = new Dimension();
		dimension2D.setSize(page.getCanvas().getClientSize().getWidth() / 2,
				page.getCanvas().getClientSize().getHeight() / 3);
		PdfTilingBrush brush = new PdfTilingBrush(dimension2D);
		brush.getGraphics().setTransparency(0.3F);
		brush.getGraphics().save();
		brush.getGraphics().translateTransform((float) brush.getSize().getWidth() / 2,
				(float) brush.getSize().getHeight() / 2);
		brush.getGraphics().rotateTransform(-45);
		brush.getGraphics().drawString(watermark, new PdfFont(PdfFontFamily.Helvetica, 24), PdfBrushes.getViolet(), 0,
				0, new PdfStringFormat(PdfTextAlignment.Center));
		brush.getGraphics().restore();
		brush.getGraphics().setTransparency(1);
		Rectangle2D loRect = new Rectangle2D.Float();
		loRect.setFrame(new Point2D.Float(0, 0), page.getCanvas().getClientSize());
		page.getCanvas().drawRectangle(brush, loRect);
	}

	/**
	 * 添加图片水印
	 * 
	 * @param pdf
	 * @throws Exception
	 */
	static void addImageWatermark(PdfDocument pdf) throws Exception {
		// 获取第一页
		PdfPageBase page = pdf.getPages().get(0);

		// 设置背景图片
//        page.setBackgroundImage("C:\\Users\\Administrator\\Desktop\\logo.png");
		BufferedImage imageWatermark = createImage("内部绝密资料", new Font("宋体", Font.PLAIN, 32));
		page.setBackgroundImage(imageWatermark);

		// 设置背景区域
		Rectangle2D.Float rect = new Rectangle2D.Float();
		rect.setRect(280, 300, 150, 150);
		page.setBackgroundRegion(rect);

		// 保存文档
		pdf.saveToFile("测试水印2.pdf");
		pdf.close();
	}

	static void addMultiImageWatermark(PdfDocument pdf) throws Exception {
		BufferedImage imageWatermark = createImage("内部资料", new Font("宋体", Font.PLAIN, 32));
		for (int i = 0; i < pdf.getPages().getCount(); i++) {
			PdfPageBase page = pdf.getPages().get(i);

			Dimension2D dimension2D = new Dimension();
			dimension2D.setSize(page.getCanvas().getSize().getWidth() / 3, page.getCanvas().getSize().getHeight() / 3);

			PdfTilingBrush brush = new PdfTilingBrush(dimension2D);
			brush.getGraphics().setTransparency(0.2f);
			brush.getGraphics().translateTransform(brush.getSize().getWidth() / 10, brush.getSize().getHeight() / 10);
			brush.getGraphics().rotateTransform(30);

			PdfImage image = PdfImage.fromImage(imageWatermark);
			brush.getGraphics().drawImage(image, brush.getSize().getWidth() - image.getWidth() / 2,
					(brush.getSize().getHeight()) / 2);

			Rectangle2D rectangle2D = new Rectangle2D.Float();
			rectangle2D.setFrame(new Point(0, 0), page.getCanvas().getClientSize());

			page.getCanvas().drawRectangle(brush, rectangle2D);
		}
		// 保存文档
		pdf.saveToFile("测试水印3.pdf");
		pdf.dispose();
	}

	/**
	 * 文字转换成图片
	 * 
	 * @param str
	 * @param font
	 * @param width
	 * @param height
	 * @return
	 * @throws Exception
	 */
	static BufferedImage createImage(String str, Font font) throws Exception {
		// 创建图片
		Integer width = str.length() * 40;
		Integer height = 100;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		g.setClip(0, 0, width, height);
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);// 先用黑色填充整张图片,也就是背景
		g.setColor(Color.black);// 在换成黑色
		g.setFont(font);// 设置画笔字体
		/** 用于获得垂直居中y */
		Rectangle clip = g.getClipBounds();
		FontMetrics fm = g.getFontMetrics(font);
		int ascent = fm.getAscent();
		int descent = fm.getDescent();
		int y = (clip.height - (ascent + descent)) / 2 + ascent;
		for (int i = 0; i < 6; i++) {// 256 340 0 680
			g.drawString(str, i * 680, y);// 画出字符串
		}
		g.dispose();
		return image;
	}

}
