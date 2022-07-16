package org.dante.springboot.word2pdf.poixwpf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.dante.springboot.word2pdf.Consts;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

/**
 * 不成功
 * 
 * https://vimsky.com/zh-tw/examples/detail/java-class-org.apache.poi.xwpf.converter.pdf.PdfConverter.html
 * 
 * @author dante
 *
 */
public class WordToPDF {

	public static void main(String[] args) {
		/*
		long startTime = System.currentTimeMillis();

		String filepath = Consts.WORD_DIR + "系统建设方案.docx";
		String outpath = Consts.WORD_DIR + "poixwpf/系统建设方案.pdf";
		try {
			// 1) Load docx with POI XWPFDocument
			XWPFDocument document = new XWPFDocument(new FileInputStream(filepath));

			// 2) Convert POI XWPFDocument 2 PDF with iText
			File outFile = new File(outpath);
			outFile.getParentFile().mkdirs();

			OutputStream out = new FileOutputStream(outFile);
			PdfOptions options = PdfOptions.create().fontEncoding("windows-1250");
			PdfConverter.getInstance().convert(document, out, options);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		System.out.println("生成系统建设方案.pdf，用时 " + (System.currentTimeMillis() - startTime) + " 毫秒。");
		*/
		wordToPdf2(Consts.WORD_DIR + "系统建设方案.docx", Consts.WORD_DIR + "poixwpf");
	}
	
	public static String wordToPdf2(String filePath, String cachePath) {
        FileInputStream fileInputStream = null;
        FileOutputStream  fileOutputStream = null;
        String outPath = "";
        try {
            File file = new File(filePath);
            // 读取docx文件
            fileInputStream = new FileInputStream(file);
            XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
            PdfOptions pdfOptions = PdfOptions.create();
            // 输出路径
            outPath = cachePath + File.separator + file.getName() + ".pdf";
            fileOutputStream = new FileOutputStream(outPath);
            // 调用转换
            PdfConverter.getInstance().convert(xwpfDocument,fileOutputStream,pdfOptions);
            fileInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outPath;
    }

}
