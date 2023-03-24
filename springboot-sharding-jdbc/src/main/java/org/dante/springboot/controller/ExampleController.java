package org.dante.springboot.controller;

import java.sql.SQLException;
import java.util.List;

import org.dante.springboot.entity.Address;
import org.dante.springboot.entity.Order;
import org.dante.springboot.service.ExampleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ExampleController {
	
	private final ExampleService exampleService;
	
	
	@GetMapping("/init")
	public void initTable() throws SQLException {
		exampleService.initEnvironment();
	}
	
	@GetMapping("/clear")
	public void clear() throws SQLException {
		exampleService.cleanEnvironment();
	}
	
	
	@GetMapping("/add")
	public void addData() throws SQLException {
		exampleService.insertData();
	}
	
	@GetMapping("/list")
	public List<Order> selectList() throws SQLException {
		return exampleService.selectAll();
	}
	
	@GetMapping("/list_join")
	public List<Order> selectListJoin() throws SQLException {
		return exampleService.selectJoin();
	}
	
	@GetMapping("/del/{orderId}")
	public void delOrder(@PathVariable Long orderId) throws SQLException {
		exampleService.deleteData(orderId);
	}
	
	@GetMapping("/update_address/{addressId}")
	public Address updateAddress(@PathVariable Long addressId) throws SQLException {
		Address address = new Address();
		address.setAddressId(addressId);
		address.setAddressName("新地址 - " + DateUtil.now());
		return exampleService.updateAddress(address);
	}
	
}
