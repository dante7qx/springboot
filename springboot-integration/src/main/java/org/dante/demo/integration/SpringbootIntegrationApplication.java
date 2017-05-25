package org.dante.demo.integration;

import static java.lang.System.getProperty;
import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.file.Files;
import org.springframework.integration.dsl.mail.Mail;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.scheduling.PollerMetadata;

import com.rometools.rome.feed.synd.SyndEntry;

@SpringBootApplication
public class SpringbootIntegrationApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootIntegrationApplication.class, args);
	}
	
	@Value("https://spring.io/blog.atom") // 1: 新闻聚合数据
	Resource resource;
	
	@Bean(name = PollerMetadata.DEFAULT_POLLER)
	public PollerMetadata poller() { // 2：使用Fluent API 和 Pollers配置默认的轮询方式
		return Pollers.fixedRate(500).get();
	}
	
	@Bean
	public FeedEntryMessageSource feedMessageSource() throws IOException { //3：数据输入
		FeedEntryMessageSource messageSource = new FeedEntryMessageSource(resource.getURL(), "news");
		return messageSource;
	}

	@Bean
	public IntegrationFlow myFlow() throws IOException {
		return IntegrationFlows.from(feedMessageSource()) //4：流程从from方法开始
				.<SyndEntry, String> route(payload -> payload.getCategories().get(0).getName(),//5
						mapping -> mapping.channelMapping("releases", "releasesChannel") //6
								.channelMapping("engineering", "engineeringChannel")
								.channelMapping("news", "newsChannel"))

		.get(); // 7
	}
	
	@Bean
	public IntegrationFlow releasesFlow() {
		return IntegrationFlows.from(MessageChannels.queue("releasesChannel", 10)) //1
				.<SyndEntry, String> transform(
						payload -> "《" + payload.getTitle() + "》 " + payload.getLink() + getProperty("line.separator")) //2
				.handle(Files.outboundAdapter(new File("/Users/dante/Documents/Project/spring/springboot/springboot-integration/src/main/resources/springblog")) //3
						.fileExistsMode(FileExistsMode.APPEND) //4
						.charset("UTF-8") //5
						.fileNameGenerator(message -> "releases.txt") //6
						.get())
				.get();
	}

	@Bean
	public IntegrationFlow engineeringFlow() {
		return IntegrationFlows.from(MessageChannels.queue("engineeringChannel", 10))
				.<SyndEntry, String> transform(
						payload -> "《" + payload.getTitle() + "》 " + payload.getLink() + getProperty("line.separator"))
				.handle(Files.outboundAdapter(new File("/Users/dante/Documents/Project/spring/springboot/springboot-integration/src/main/resources/springblog"))
						.fileExistsMode(FileExistsMode.APPEND)
						.charset("UTF-8")
						.fileNameGenerator(message -> "engineering.txt")
						.get())
				.get();
	}

	@Bean
	public IntegrationFlow newsFlow() {
		return IntegrationFlows.from(MessageChannels.queue("newsChannel", 10))
				.<SyndEntry, String> transform(
						payload -> "《" + payload.getTitle() + "》 " + payload.getLink() + getProperty("line.separator"))
				.enrichHeaders( //1
						Mail.headers()
						.subject("来自Spring的新闻")
						.to("sunchao.0129@163.com")
						.from("sunchao.0129@163.com"))
				.handle(Mail.outboundAdapter("smtp.163.com") //2
						.port(25)
						.protocol("smtp")
						.credentials("sunchao.0129", "Dante2012")
						.javaMailProperties(p -> p.put("mail.debug", "false")), e -> e.id("smtpOut"))
				.get();
	}
}
