package org.dante.springboot.word2pdf.document4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.dante.springboot.word2pdf.Consts;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;

/**
 * 如果想在一组不受约束的文件上保持高保真度，你需要使用微软自己的 Office 布局引擎。
 * 
 * https://developer.microsoft.com/en-us/graph/examples/document-conversion
 * 
 * Document4j 是在转换机器上使用 Office 和 Microsoft Scripting Host for VBS，运行服务的机器必须使用 Microsoft Windows
 * 
 * @author dante
 *
 */
public class WordToPDF {
	

	/**
	 * 因为本机是 MacOS，所以无法进行转换
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		File inputWord = new File(Consts.WORD_DIR.concat("系统建设方案.docx"));
		File outputFile = new File(Consts.WORD_DIR.concat("docuemnt4j/系统建设方案.pdf"));
		try {
			InputStream docxInputStream = new FileInputStream(inputWord);
			OutputStream outputStream = new FileOutputStream(outputFile);
			IConverter converter = LocalConverter.builder().build();
			converter.convert(docxInputStream).as(DocumentType.DOCX).to(outputStream).as(DocumentType.PDF).execute();
			outputStream.close();
			System.out.println("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
