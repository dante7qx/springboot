package org.dante.springboot.word2pdf.aspose;

import org.dante.springboot.word2pdf.Consts;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

/**
 * Aspose
 * 
 * https://www.aspose.app/
 * 
 * @author dante
 *
 */
public class WordToPDF {
	
	public static void main(String[] args) throws Exception{
        
		Document wpd = new Document(Consts.WORD_DIR + "系统建设方案.docx");

	     // Convert DOC to PDF
		wpd.save(Consts.WORD_DIR + "aspose/系统建设方案.pdf", SaveFormat.PDF);
    }
}
