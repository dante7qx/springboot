package org.dante.springboot.word2pdf.aspose;

import java.io.OutputStream;
import java.util.List;

import org.springframework.util.Assert;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import cn.hutool.core.collection.CollUtil;

/**
 * Word 合并工具类
 * 
 * @author dante
 *
 */
public class WordMergeUtil {
	
	/**
	 * 合并多个Word文件，生成一个新的Word文件
	 * 
	 * @param sourceWordFiles 要合并的Word文件集合
	 * @param targetWord	  合并后的Word文件
	 */
	public static void mergeToFile(List<String> sourceWordFiles, String targetWord) {
		Assert.hasText(targetWord, "合并后的Word文件不能为空！");
		if(CollUtil.isEmpty(sourceWordFiles) || sourceWordFiles.size() == 1) {
			throw new IllegalArgumentException("要合并的Word文件必须多于一个！");
		}
		
		// 加载第一个文档到Document对象
		Document doc = new Document(sourceWordFiles.get(0));
		int size = sourceWordFiles.size();
		for (int i = 1; i < size; i++) {
			doc.insertTextFromFile(sourceWordFiles.get(i), FileFormat.Docx);
		}
		doc.saveToFile(targetWord, FileFormat.Docx);
		doc.close();
	}
	
	/**
	 * 合并多个Word文件，生成一个新的Word文件
	 * 
	 * @param sourceWordFiles 要合并的Word文件集合
	 * @param outputStream	  合并后的Word文件输出到流中
	 */
	public static void mergeToStream(List<String> sourceWordFiles, OutputStream outputStream) {
		Assert.notNull(outputStream, "输出流不能为空！");
		if(CollUtil.isEmpty(sourceWordFiles) || sourceWordFiles.size() == 1) {
			throw new IllegalArgumentException("要合并的Word文件必须多于一个！");
		}
		
		// 加载第一个文档到Document对象
		Document doc = new Document(sourceWordFiles.get(0));
		int size = sourceWordFiles.size();
		for (int i = 1; i < size; i++) {
			doc.insertTextFromFile(sourceWordFiles.get(i), FileFormat.Docx);
		}
		doc.saveToStream(outputStream, FileFormat.Docx);
		doc.close();
	}
}
