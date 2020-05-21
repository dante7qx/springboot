package org.dante.springboot.zookeeper.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;
import org.dante.springboot.zookeeper.config.ZookeeperConfig;
import org.dante.springboot.zookeeper.service.ZookeeperService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ZookeeperServiceImpl implements ZookeeperService {

	@Override
	public boolean isExistNode(String path) {
		CuratorFramework client = ZookeeperConfig.getClient();
		client.sync();
		try {
			client.checkExists().forPath(wrapperPath(path));
			return client.checkExists().forPath(path) != null;
		} catch (Exception e) {
			log.error("isExistNode error...", e);
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void createNode(CreateMode mode, String path) {
		CuratorFramework client = ZookeeperConfig.getClient();
		try {
			// 递归创建所需父节点
			client.create().creatingParentsIfNeeded().withMode(mode).forPath(wrapperPath(path));
		} catch (Exception e) {
			log.error("createNode error...", e);
			e.printStackTrace();
		}

	}

	@Override
	public void setNodeData(String path, String nodeData) {
		CuratorFramework client = ZookeeperConfig.getClient();
		try {
			// 设置节点数据
			client.setData().forPath(wrapperPath(path), nodeData.getBytes("UTF-8"));
		} catch (Exception e) {
			log.error("setNodeData error...", e);
			e.printStackTrace();
		}
	}

	@Override
	public void createNodeAndData(CreateMode mode, String path, String nodeData) {
		CuratorFramework client = ZookeeperConfig.getClient();
		try {
			// 创建节点，关联数据
			client.create().creatingParentsIfNeeded().withMode(mode).forPath(wrapperPath(path), nodeData.getBytes("UTF-8"));
		} catch (Exception e) {
			log.error("createNode error...", e);
			e.printStackTrace();
		}
	}

	@Override
	public String getNodeData(String path) {
		CuratorFramework client = ZookeeperConfig.getClient();
		try {
			// 数据读取和转换
			byte[] dataByte = client.getData().forPath(wrapperPath(path));
			String data = new String(dataByte, "UTF-8");
			if (!StringUtils.isEmpty(data)) {
				return data;
			}
		} catch (Exception e) {
			log.error("getNodeData error...", e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getNodeChild(String path) {
		CuratorFramework client = ZookeeperConfig.getClient();
		List<String> nodeChildDataList = new ArrayList<>();
		try {
			// 节点下数据集
			nodeChildDataList = client.getChildren().forPath(wrapperPath(path));
		} catch (Exception e) {
			log.error("getNodeChild error...", e);
			e.printStackTrace();
		}
		return nodeChildDataList;
	}

	@Override
	public void deleteNode(String path, Boolean recursive) {
		CuratorFramework client = ZookeeperConfig.getClient();
		try {
			if (recursive) {
				// 递归删除节点
				client.delete().guaranteed().deletingChildrenIfNeeded().forPath(wrapperPath(path));
			} else {
				// 删除单个节点
				client.delete().guaranteed().forPath(wrapperPath(path));
			}
		} catch (Exception e) {
			log.error("deleteNode error...", e);
			e.printStackTrace();
		}
	}

	@Override
	public InterProcessReadWriteLock getReadWriteLock(String path) {
		CuratorFramework client = ZookeeperConfig.getClient();
		// 写锁互斥、读写互斥
		InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, path);
		return readWriteLock;
	}
	
	private String wrapperPath(String path) {
		return "/".concat(path);
	}

}
