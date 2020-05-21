package org.dante.springboot.zookeeper.controller;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.dante.springboot.zookeeper.service.ZookeeperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/zk")
public class ZookeeperController {

	@Autowired
	private ZookeeperService zookeeperService;

	/**
	 * 查询节点数据
	 * 
	 * @param path
	 * @return
	 */
	@GetMapping("/get_node_data/{path}")
	public String getNodeData(@PathVariable String path) {
		return zookeeperService.getNodeData(path);
	}

	/**
	 * 判断节点是否存在
	 * 
	 * @param path
	 * @return
	 */
	@GetMapping("/exist_node/{path}")
	public boolean isExistNode(@PathVariable String path) {
		return zookeeperService.isExistNode(path);
	}

	/**
	 * 创建节点
	 * 
	 * @param mode
	 * @param path
	 * @return
	 */
	@GetMapping("/create_node/{type}/{path}")
	public String createNode(@PathVariable Integer type, @PathVariable String path) {
		try {
			zookeeperService.createNode(CreateMode.fromFlag(type), path);
		} catch (KeeperException e) {
			log.error(e.getMessage(), e);
		}
		return "Success";
	}

	/**
	 * 设置节点数据
	 * 
	 * @param path
	 * @param nodeData
	 * @return
	 */
	@GetMapping("/set_node_data/{path}/{data}")
	public String setNodeData(@PathVariable String path, @PathVariable String data) {
		zookeeperService.setNodeData(path, data);
		return "success";
	}

	/**
	 * 递归获取节点数据
	 * 
	 * @param mode
	 * @param path
	 * @param nodeData
	 * @return
	 */
	@GetMapping("/create_node_with_data/{type}/{path}/{data}")
	public String createNodeAndData(@PathVariable Integer type, @PathVariable String path, @PathVariable String data) {
		try {
			zookeeperService.createNodeAndData(CreateMode.fromFlag(type), path, data);
		} catch (KeeperException e) {
			log.error(e.getMessage(), e);
		}
		return "success";
	}

	/**
	 * 递归获取节点数据
	 * 
	 * @param path
	 * @return
	 */
	@GetMapping("/get_node_child/{path}")
	public List<String> getNodeChild(@PathVariable String path) {
		return zookeeperService.getNodeChild(path);
	}

	/**
	 * 是否递归删除节点
	 * 
	 * @param path
	 * @param recursive
	 * @return
	 */
	@GetMapping("/delete_node/{path}/{recursive}")
	public String deleteNode(@PathVariable String path, @PathVariable Boolean recursive) {
		zookeeperService.deleteNode(path, recursive);
		return "success";
	}

}
