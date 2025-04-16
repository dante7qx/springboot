package org.dante.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import cn.hutool.core.lang.Console;

@SpringBootTest
public class SpringbootActCaptchaApplicationTests {

	@Autowired
    private ImageCaptchaApplication application;

	@Test
    void test() {
        // 1.生成滑块验证码(该数据返回给前端用于展示验证码数据)
        CaptchaResponse<ImageCaptchaVO> res1 = application.generateCaptcha(CaptchaTypeConstant.SLIDER);
        
        Console.log(res1.getCaptcha().getData());
		
        // 2.前端滑动完成后把数据传入后端进行校验是否通过， 
        // 	参数1: 生成的验证码对应的id, 由前端传过来
        // 	参数2: 滑动轨迹验证码相关数据 ImageCaptchaTrack， 由前端传过来
        // 返回 match.isSuccess() 如果为true， 则验证通过
        ImageCaptchaTrack sliderCaptchaTrack = new ImageCaptchaTrack();
        ApiResponse<?> match = application.matching(res1.getId(), sliderCaptchaTrack);
        Console.log(match.isSuccess());
    }
	
}
