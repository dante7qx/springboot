package org.dante.springboot.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.dante.springboot.util.QrCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class QrCodeController {

	/**
	 * 根据 url 生成 普通二维码
	 * 
	 * @param response
	 * @param url
	 * @throws Exception
	 */
    @GetMapping("/create_common_qrcode")
    public void createCommonQRCode(String url, HttpServletResponse response) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            //使用工具类生成二维码
            QrCodeUtil.encode(url, stream);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }
    
    /**
     * 根据 url 生成 带有logo二维码
     * 
     * @param response
     * @param url
     * @throws Exception
     */
    @GetMapping("/create_logo_qrcode")
    public void createLogoQRCode(HttpServletResponse response, String url) throws Exception {
        ServletOutputStream stream = null;
        try {
            stream = response.getOutputStream();
            String logoPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                    + "static" + File.separator + "cat.jpg";
            //使用工具类生成二维码
            QrCodeUtil.encode(url, logoPath, stream, true);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
        } finally {
            if (stream != null) {
                stream.flush();
                stream.close();
            }
        }
    }
    
    @PostMapping("/generator_code")
    @ResponseBody
    public String createQRCode(@RequestBody String content) throws Exception {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	String code = "";
        try {
            log.info("二维码内容：{}", content);
            QrCodeUtil.encode(content, bos);
            code = Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (bos != null) {
            	bos.flush();
            	bos.close();
            }
        }
        return code;
    }

    @PostMapping("/generator_logo_code")
    @ResponseBody
    public String createLogoQRCode(@RequestBody String content) throws Exception {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	String code = "";
        try {
            String logoPath = Thread.currentThread().getContextClassLoader().getResource("").getPath()
                    + "static" + File.separator + "cat.jpg";
            log.info("二维码内容：{}, Logo {}", content, logoPath);
            QrCodeUtil.encode(content, logoPath, bos, true);
            code = Base64.getEncoder().encodeToString(bos.toByteArray());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (bos != null) {
            	bos.flush();
            	bos.close();
            }
        }
        return code;
    }
}
