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
        
		Document wpd = new Document(Consts.WORD_DIR + "合并文档.docx");

	     // Convert DOC to PDF
		wpd.save(Consts.WORD_DIR + "aspose/合并文档.pdf", SaveFormat.PDF);
    }
}
