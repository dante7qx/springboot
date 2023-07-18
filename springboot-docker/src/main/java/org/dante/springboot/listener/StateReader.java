package org.dante.springboot.listener;

import java.time.Instant;

import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

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
    private ApplicationAvailability applicationAvailability;
	
	
	@RequestMapping(value="/get")
    public String state() {
        return "livenessState : " + applicationAvailability.getLivenessState()
               + "<br>readinessState : " + applicationAvailability.getReadinessState()
               + "<br>" + Instant.now();
    }
}
