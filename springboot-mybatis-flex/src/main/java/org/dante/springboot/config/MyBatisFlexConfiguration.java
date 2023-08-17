package org.dante.springboot.config;

import org.springframework.context.annotation.Configuration;
import com.mybatisflex.core.audit.AuditManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MyBatisFlexConfiguration {
	
	public MyBatisFlexConfiguration() {
        // 开启审计功能
        AuditManager.setAuditEnable(true);

        // 设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage -> log.info("{},{}ms", auditMessage.getFullSql(), auditMessage.getElapsedTime()));
    }
	
}
