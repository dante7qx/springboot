package org.dante.springboot.listener;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于获取存活和就绪状态
 * 
 * @author dante
 *
 */
@RestController
@RequestMapping("/statereader")
public class StateReader {
	
	@Resource
    ApplicationAvailability applicationAvailability;
	
	
	@RequestMapping(value="/get")
    public String state() {
        return "livenessState : " + applicationAvailability.getLivenessState()
               + "<br>readinessState : " + applicationAvailability.getReadinessState()
               + "<br>" + new Date();
    }
}
