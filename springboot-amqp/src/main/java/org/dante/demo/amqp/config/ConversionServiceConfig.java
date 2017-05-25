package org.dante.demo.amqp.config;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;

/**
 * 自定义 ConversionService（方法参数消息转换器）
 * 
 * @author dante
 *
 */
//@Configuration
//@EnableRabbit
public class ConversionServiceConfig implements RabbitListenerConfigurer {
	
	/*
	@Bean
    public DefaultMessageHandlerMethodFactory handlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(new GenericMessageConverter(myConversionService()));
        return factory;
    }
	
	@Bean
    public ConversionService myConversionService() {
        DefaultConversionService conv = new DefaultConversionService();
        conv.addConverter(mySpecialConverter());
        return conv;
    }
	*/
	
	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
//		registrar.setMessageHandlerMethodFactory(handlerMethodFactory());
	}
	
}
