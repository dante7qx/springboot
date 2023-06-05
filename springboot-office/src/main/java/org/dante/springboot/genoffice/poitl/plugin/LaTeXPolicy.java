package org.dante.springboot.genoffice.poitl.plugin;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.policy.RenderPolicy;
import com.deepoove.poi.template.ElementTemplate;
import com.deepoove.poi.template.run.RunTemplate;


/**
 * 数学公式插件
 * 
 * @author dante
 *
 */
public class LaTeXPolicy implements RenderPolicy {

	@Override
	public void render(ElementTemplate eleTemplate, Object data, XWPFTemplate template) {
		XWPFRun run = ((RunTemplate) eleTemplate).getRun(); 

		// 将 data 转成 OMath 格式
		 String mathMlContent = String.valueOf(data);
	    
	    CTOMath math = CTOMath.Factory.newInstance();
//	    String mathMlContent = "<m:oMath xmlns:m=\"http://schemas.openxmlformats.org/officeDocument/2006/math\">"
//        		+ "<m:f><m:fPr><m:ctrlPr></m:ctrlPr></m:fPr><m:num><m:r><m:rPr><m:sty m:val=\"p\"/></m:rPr><m:t>d</m:t></m:r></m:num><m:den><m:r><m:rPr><m:sty m:val=\"p\"/></m:rPr><m:t>d</m:t></m:r><m:r><m:t>x</m:t></m:r></m:den></m:f><m:sSup><m:sSupPr><m:ctrlPr></m:ctrlPr></m:sSupPr><m:e><m:r><m:t>x</m:t></m:r></m:e><m:sup><m:r><m:t>n</m:t></m:r></m:sup></m:sSup><m:r><m:t>=n</m:t></m:r><m:sSup><m:sSupPr><m:ctrlPr></m:ctrlPr></m:sSupPr><m:e><m:r><m:t>x</m:t></m:r></m:e><m:sup><m:r><m:t>n-1</m:t></m:r></m:sup></m:sSup>"
//        		+ "</m:oMath>";
	    try {
			math.set(CTOMath.Factory.parse(mathMlContent));
//			run.getCTR().addNewObject().set(math);
			run.getCTR().set(math);
		} catch (XmlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
