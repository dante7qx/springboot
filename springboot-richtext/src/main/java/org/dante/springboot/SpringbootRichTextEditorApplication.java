package org.dante.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 富文本编辑器
 * 1. Markdown 编辑器，
 * 参考：
 * tui.editor
 *   https://nhn.github.io/tui.editor/latest/ToastUIEditor
 *   https://github.com/nhn/tui.editor
 *   
 * vue-markdown
 *   https://github.com/zhaoxuhui1122/vue-markdown
 *   
 * 文档目录
 *   https://blog.csdn.net/qq_34414994/article/details/105123840
 *   https://www.geek-share.com/detail/2767146896.html
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
