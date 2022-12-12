package org.dante.springboot.word2pdf.aspose;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.graphics.PdfTilingBrush;
import com.spire.pdf.widget.PdfPageCollection;

/**
 * Word 添加水印工具类
 * 
 * @author dante
 *
 */
public class PdfWaterMarkUtil {

	private static final String TXT = "txt";
	private static final String IMG = "img";

	/**
	 * 添加文字水印
	 * 
	 * @param sourcePath
	 * @param targetPath
	 * @param watermark
	 */
	public static void addTextWatermark(String sourcePath, String targetPath, String watermark) {
		PdfDocument pdf = addWatermark(sourcePath, TXT, watermark);
		pdf.saveToFile(targetPath);
		pdf.close();
	}

	/**
	 * 添加文字水印
	 * 
	 * @param sourcePath
	 * @param targetPath
	 * @param watermark
	 */
	public static void addTextWatermarkToStream(String sourcePath, OutputStream outputStream, String watermark) {
		PdfDocument pdf = addWatermark(sourcePath, TXT, watermark);
		pdf.saveToStream(outputStream);
		pdf.close();
	}

	/**
	 * 添加文字水印
	 * 
	 * @param sourcePath
	 * @param targetPath
	 * @param watermark
	 */
	public static void addMultiTextWatermark(String sourcePath, String targetPath, String watermark) {
		PdfDocument pdf = addMultiWatermark(sourcePath, TXT, watermark);
		pdf.saveToFile(targetPath);
		pdf.dispose();
	}

	/**
	 * 添加文字水印
	 * 
	 * @param sourcePath
	 * @param targetPath
	 * @param watermark
	 */
	public static void addMultiTextWatermarkToStream(String sourcePath, OutputStream outputStream, String watermark) {
		PdfDocument pdf = addMultiWatermark(sourcePath, TXT, watermark);
		pdf.saveToStream(outputStream);
		pdf.dispose();
	}

	/**
	 * 添加图片水印
	 * 
	 * @param sourcePath
	 * @param targetPath
	 * @param watermark
	 */
	public static void addImageWatermark(String sourcePath, String targetPath, String imageWatermarkPath) {
		PdfDocument pdf = addWatermark(sourcePath, IMG, imageWatermarkPath);
		pdf.saveToFile(targetPath);
		pdf.close();
	}

	/**
	 * 添加图片水印
	 * 
	 * @param sourcePath
	 * @param targetPath
	 * @param watermark
	 */
	public static void addMultiImageWatermark(String sourcePath, String targetPath, String imageWatermarkPath) {
		PdfDocument pdf = addMultiWatermark(sourcePath, IMG, imageWatermarkPath);
		pdf.saveToFile(targetPath);
		pdf.dispose();
	}

	/**
	 * 添加图片水印
	 * 
	 * @param sourcePath
	 * @param targetPath
	 * @param watermark
	 */
	public static void addImageWatermarkToStream(String sourcePath, OutputStream outputStream,
			String imageWatermarkPath) {
		PdfDocument pdf = addWatermark(sourcePath, IMG, imageWatermarkPath);
		pdf.saveToStream(outputStream);
		pdf.close();
	}

	/**
	 * 添加图片水印
	 * 
	 * @param sourcePath
	 * @param targetPath
	 * @param watermark
	 */
	public static void addMultiImageWatermarkToStream(String sourcePath, OutputStream outputStream,
			String imageWatermarkPath) {
		PdfDocument pdf = addMultiWatermark(sourcePath, IMG, imageWatermarkPath);
		pdf.saveToStream(outputStream);
		pdf.dispose();
	}

	static PdfDocument addWatermark(String sourcePath, String type, String watermark) {
		PdfDocument pdf = new PdfDocument();
		pdf.loadFromFile(sourcePath);
		PdfPageCollection pages = pdf.getPages();
		BufferedImage imageWatermark = null;
		if (TXT.equals(type)) {
			imageWatermark = txt2Image(watermark, 100);
		}

		for (Object pageObj : pages) {
			PdfPageBase page = (PdfPageBase) pageObj;
			if (TXT.equals(type)) {
				page.setBackgroundImage(imageWatermark);
			} else {
				page.setBackgroundImage(watermark);
			}
			// 设置背景区域
			Rectangle2D.Float rect = new Rectangle2D.Float();
			rect.setRect(280, 300, 150, 150);
			page.setBackgroundRegion(rect);
		}
		return pdf;
	}

	static PdfDocument addMultiWatermark(String sourcePath, String type, String watermark) {
		PdfDocument pdf = new PdfDocument();
		pdf.loadFromFile(sourcePath);
		BufferedImage imageWatermark = null;
		if (TXT.equals(type)) {
			imageWatermark = txt2Image(watermark, 50);
		}
		for (int i = 0; i < pdf.getPages().getCount(); i++) {
			PdfPageBase page = pdf.getPages().get(i);

			Dimension2D dimension2D = new Dimension();
			dimension2D.setSize(page.getCanvas().getSize().getWidth() / 3, page.getCanvas().getSize().getHeight() / 3);

			PdfTilingBrush brush = new PdfTilingBrush(dimension2D);
			brush.getGraphics().setTransparency(0.2f);
			brush.getGraphics().translateTransform(brush.getSize().getWidth() / 10, brush.getSize().getHeight() / 10);
			brush.getGraphics().rotateTransform(30);

			PdfImage image = null;
			if (TXT.equals(type)) {
				image = PdfImage.fromImage(imageWatermark);
			} else {
				image = PdfImage.fromImage(watermark);
			}
			brush.getGraphics().drawImage(image, brush.getSize().getWidth() - image.getWidth() / 2,
					(brush.getSize().getHeight()) / 2);

			Rectangle2D rectangle2D = new Rectangle2D.Float();
			rectangle2D.setFrame(new Point(0, 0), page.getCanvas().getClientSize());
			page.getCanvas().drawRectangle(brush, rectangle2D);
		}
		return pdf;
	}

	/**
	 * 文字转换成图片
	 * 
	 * @param str
	 * @param font
	 * @param height
	 * @return
	 * @throws Exception
	 */
	static BufferedImage txt2Image(String str, Integer height) {
		Integer width = str.length() * 40;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Font font = new Font("宋体", Font.PLAIN, 32);
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
