package org.dante.demo.amqp.constant;

public interface RabbitMQConstant {

	public final static String HELLO_QUEUE = "boot_dante_hello";
	public final static String CONTAINER_QUEUE = "boot_dante_container";
	public final static String TASK_QUEUE = "task_queue";

	/**
	 * Direct 交换器：按照routingKey将消息分发到指定的队列（队列可以在绑定时自动声明）
	 */
	public final static String DIRECT_EXCHANGE = "boot_dante_direct_exchange";
	public final static String DIRECT_ROUTING_KEY = "boot_dante_direct_routing";
	public final static String DIRECT_NOT_DECLARE_QUEUE_ROUTING_KEY = "boot_dante_not_declare_direct_routing";
	public final static String DIRECT_QUEUE = "boot_dante_direct_queue";

	/**
	 * Topic 交换器：按照routingKey将消息分发到指定的队列，其中routingKey可设置RabbitMQ特定的多关键字匹配规则 其中：
	 * 1 * 只能匹配一个单词 
	 * 2 # 匹配零个或多个单词
	 */
	public final static String TOPIC_EXCHANGE = "boot_dante_topic_exchange";
	public final static String TOPIC_ROUTING_KEY_STAR = "*.boot_dante_direct_routing.*";
	public final static String TOPIC_ROUTING_KEY_JING = "*.boot_dante_direct_routing.#";
	public final static String TOPIC_ROUTING_KEY = ".boot_dante_direct_routing.";

	/**
	 * Fanout 交换器：消息广播，无routingKey的概念，消息会分发到所有绑定fanout交换器的队列上
	 */
	public final static String FANOUT_EXCHANGE = "boot_dante_fanout_exchange";

	/**
	 * 消息应答模式
	 */
	public final static String CONFIRM_EXCHANGE = "boot_dante_confirm_exchange";
	public final static String CONFIRM_ROUTING_KEY = "boot_dante_confirm_routing_key";
	public final static String CONFIRM_QUEUE = "boot_dante_confirm_queue";
}
