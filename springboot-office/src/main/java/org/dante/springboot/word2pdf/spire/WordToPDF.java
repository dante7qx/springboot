package org.dante.springboot.word2pdf.spire;

import org.dante.springboot.word2pdf.Consts;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

/**
 * 冰蓝科技
 * 
 * http://www.e-iceblue.cn
 * 
 * @author dante
 *
 */
public class WordToPDF {
	
	public static void main(String[] args) {
        //实例化Document类的对象
        Document doc = new Document();

        //加载Word
        doc.loadFromFile(Consts.WORD_DIR.concat("系统建设方案.docx"));

        //保存为PDF格式
        doc.saveToFile(Consts.WORD_DIR.concat("spire/系统建设方案.pdf"),FileFormat.PDF);
    }
}
