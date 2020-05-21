package org.dante.springboot.zookeeper.service;

import java.util.List;

import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.zookeeper.CreateMode;

public interface ZookeeperService {
	/**
	 * 判断节点是否存在
	 */
	boolean isExistNode(final String path);

	/**
	 * 创建节点
	 * 
	 * 节点类型
	 * 0 - PERSISTENT 持久化目录节点，存储的数据不会丢失。
	 * 2 - PERSISTENT_SEQUENTIAL顺序自动编号的持久化目录节点，存储的数据不会丢失
	 * 1 - EPHEMERAL临时目录节点，一旦创建这个节点的客户端与服务器端口也就是session 超时，这种节点会被自动删除
	 * 3 - EPHEMERAL_SEQUENTIAL临时自动编号节点，一旦创建这个节点的客户端与服务器端口也就是session 超时，这种节点会被自动删除，并且根据当前已近存在的节点数自动加 1，然后返回给客户端已经成功创建的目录节点名。
	 */
	void createNode(CreateMode mode, String path);

	/**
	 * 设置节点数据
	 */
	void setNodeData(String path, String nodeData);

	/**
	 * 创建节点
	 */
	void createNodeAndData(CreateMode mode, String path, String nodeData);

	/**
	 * 获取节点数据
	 */
	String getNodeData(String path);

	/**
	 * 获取节点下数据
	 */
	List<String> getNodeChild(String path);

	/**
	 * 是否递归删除节点
	 */
	void deleteNode(String path, Boolean recursive);

	/**
     * 获取读写锁
     */
    InterProcessReadWriteLock getReadWriteLock (String path);
}
