package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 富文本编辑器
 * 1. Markdown 编辑器，
 * 参考：
 *   https://nhn.github.io/tui.editor/latest/ToastUIEditor
 *   https://github.com/nhn/tui.editor
 * 
 * @author dante
 *
 */
@SpringBootApplication
public class SpringbootRichTextEditorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRichTextEditorApplication.class, args);
	}
}
