package org.dante.springboot.zookeeper.config;

import javax.annotation.PostConstruct;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class ZookeeperConfig {

	@Autowired
	private ZookeeperProperties zookeeperProperties;

	private static CuratorFramework client = null;
	
	public TreeCache cache;

	/**
	 * 初始化
	 * @throws Exception 
	 */
	@PostConstruct
	public void init() throws Exception {
		// 重试策略，初试时间1秒，重试2次
		RetryPolicy policy = new ExponentialBackoffRetry(zookeeperProperties.getBaseSleepTimeMs(),
				zookeeperProperties.getMaxRetries());
		// 通过工厂创建Curator
		client = CuratorFrameworkFactory.builder().connectString(zookeeperProperties.getServer()).retryPolicy(policy)
				.authorization("digest", zookeeperProperties.getDigest().getBytes())
				.connectionTimeoutMs(zookeeperProperties.getConnectionTimeoutMs())
				.sessionTimeoutMs(zookeeperProperties.getSessionTimeoutMs())
				.namespace(zookeeperProperties.getNamespace()).build();
		// 开启连接
		client.start();
		log.info("zookeeper 初始化完成...");
		
//		initLocalCache("/test");
		
		client.getConnectionStateListenable().addListener((cli, state) -> {
				if (state == ConnectionState.LOST) {
					//连接丢失
					log.info("和Zookeeper的连接丢失");
				} else if (state == ConnectionState.CONNECTED) {
					//连接新建
					log.info("新建和Zookeeper的连接");
				} else if (state == ConnectionState.RECONNECTED) {
					log.info("重新和Zookeeper建立连接");
				}
			}
        );
	}

	public static CuratorFramework getClient() {
		return client;
	}

	public static void closeClient() {
		if (client != null) {
			client.close();
		}
	}

	/**
	 * 初始化本地缓存
	 * 
	 * @param watchRootPath
	 * @throws Exception
	 */
	private void initLocalCache(String watchRootPath) throws Exception {
		cache = new TreeCache(client, watchRootPath);
		TreeCacheListener listener = (client1, event) -> {
			log.info("event:" + event.getType() + " |path:"
					+ (null != event.getData() ? event.getData().getPath() : null));

			if (event.getData() != null && event.getData().getData() != null) {
				log.info("发生变化的节点内容为：" + new String(event.getData().getData()));
			}

			// client1.getData().
		};
		cache.getListenable().addListener(listener);
		cache.start();
	}

}
