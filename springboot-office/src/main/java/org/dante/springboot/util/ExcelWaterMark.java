package org.dante.springboot.util;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.xssf.usermodel.XSSFPictureData;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.spire.xls.ExcelVersion;
import com.spire.xls.ViewMode;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelWaterMark {
	
	private final static String DIR = "/Users/dante/Documents/Project/java-world/springboot/springboot-office/src/main/java/org/dante/springboot/util/";

	/**
	 * 给 Excel 添加水印
	 *
	 * @param workbook      XSSFWorkbook
	 * @param waterMarkText 水印文字内容
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static void insertWaterMarkText(XSSFWorkbook workbook, String waterMarkText) throws FileNotFoundException, IOException {
		BufferedImage image = createWatermarkImage(waterMarkText);
		// 导出到字节流B
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", os);
		} catch (IOException e) {
			log.error("添加水印失败");
		}
		int pictureIdx = workbook.addPicture(os.toByteArray(), XSSFWorkbook.PICTURE_TYPE_PNG);
		XSSFPictureData pictureData = workbook.getAllPictures().get(pictureIdx);
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			// 获取每个Sheet表
			XSSFSheet sheet = workbook.getSheetAt(i);
			PackagePartName ppn = pictureData.getPackagePart().getPartName();
			String relType = XSSFRelation.IMAGES.getRelation();
			PackageRelationship pr = sheet.getPackagePart().addRelationship(ppn, TargetMode.INTERNAL, relType, null);
			sheet.getCTWorksheet().addNewPicture().setId(pr.getId());
		}
		
		workbook.write(new FileOutputStream(DIR + "water3.xlsx"));
	}

	/**
	 * 创建水印图片 excel
	 *
	 * @param waterMark 水印内容
	 */
	public static BufferedImage createWatermarkImage(String waterMark) {
		String[] textArray = waterMark.split("\n");
		java.awt.Font font = new java.awt.Font("microsoft-yahei", java.awt.Font.PLAIN, 20);
		Integer width = 500;
		Integer height = 200;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 背景透明 开始
		Graphics2D g = image.createGraphics();
		image = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
		g.dispose();
		// 背景透明 结束
		g = image.createGraphics();
		g.setColor(new Color(Integer.parseInt("#C5CBCF".substring(1), 16))); // 设定画笔颜色
		g.setFont(font); // 设置画笔字体
		g.shear(0.1, -0.26); // 设定倾斜度
		// 设置字体平滑
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int y = 150;
		for (int i = 0; i < textArray.length; i++) {
			g.drawString(textArray[i], 0, y); // 画出字符串
			y = y + font.getSize();
		}
		g.dispose(); // 释放画笔
		return image;
	}
	
	 private static BufferedImage drawText (String text, Font font, Color textColor, Color backColor,double height, double width) {
	        //定义图片宽度和高度
	        BufferedImage img = new BufferedImage((int) width, (int) height, TYPE_INT_ARGB);
	        Graphics2D loGraphic = img.createGraphics();

	        //获取文本size
	        FontMetrics loFontMetrics = loGraphic.getFontMetrics(font);
	        int liStrWidth = loFontMetrics.stringWidth(text);
	        int liStrHeight = loFontMetrics.getHeight();

	        //文本显示样式及位置
	        loGraphic.setColor(backColor);
	        loGraphic.fillRect(0, 0, (int) width, (int) height);
	        loGraphic.translate(((int) width - liStrWidth) / 2, ((int) height - liStrHeight) / 2);
	        loGraphic.rotate(Math.toRadians(-45));

	        loGraphic.translate(-((int) width - liStrWidth) / 2, -((int) height - liStrHeight) / 2);
	        loGraphic.setFont(font);
	        loGraphic.setColor(textColor);
	        loGraphic.drawString(text, ((int) width - liStrWidth) / 2, ((int) height - liStrHeight) / 2);
	        loGraphic.dispose();
	        return img;
	    }
	
	public static void main(String[] args) throws IOException {
		
		XSSFWorkbook workbook = new XSSFWorkbook(DIR + "water.xlsx");
		ExcelWaterMark.insertWaterMarkText(workbook, "你好，世界！");
		
//		spireXls();
	}
	
	private static void spireXls() {
		//加载示例文档
        Workbook workbook = new Workbook();
        workbook.loadFromFile(DIR + "water.xlsx");

        //设置文本和字体大小
        Font font = new Font("仿宋", Font.PLAIN, 40);
        String watermark = "你好，世界！";

        for (Worksheet sheet : (Iterable<Worksheet>) workbook.getWorksheets()) {
            //调用DrawText() 方法插入图片
            BufferedImage imgWtrmrk = drawText(watermark, font, Color.pink, Color.white, sheet.getPageSetup().getPageHeight(), sheet.getPageSetup().getPageWidth());

            //将图片设置为页眉
            sheet.getPageSetup().setLeftHeaderImage(imgWtrmrk);
            sheet.getPageSetup().setLeftHeader("&G");

            //将显示模式设置为Layout
            sheet.setViewMode(ViewMode.Layout);
        }

        //保存文档
        workbook.saveToFile("water2.xlsx", ExcelVersion.Version2010);
	}

}
