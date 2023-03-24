/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dante.springboot.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dante.springboot.entity.Address;
import org.dante.springboot.entity.Order;
import org.dante.springboot.entity.OrderItem;
import org.dante.springboot.repository.AddressRepository;
import org.dante.springboot.repository.OrderItemRepository;
import org.dante.springboot.repository.OrderRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public final class ExampleService {
    
    
    private final OrderRepository orderRepository;
    
    private final OrderItemRepository orderItemRepository;
    
    private final AddressRepository addressRepository;
    
    public ExampleService(final OrderRepository orderRepository, final OrderItemRepository orderItemRepository, final AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.addressRepository = addressRepository;
    }
    
//    @PostConstruct
    public void init() throws SQLException {
    	cleanEnvironment();
    	initEnvironment();
    }
    
    /**
     * 清除已有数据库表，重新创建数据库表
     * 
     * @throws SQLException
     */
    public void initEnvironment() throws SQLException {
        orderRepository.createTableIfNotExists();
        orderItemRepository.createTableIfNotExists();
        addressRepository.createTableIfNotExists();
        orderRepository.truncateTable();
        orderItemRepository.truncateTable();
        addressRepository.truncateTable();
    }
    
    public int insertOrder() {
    	for (int i = 1; i <= 10; i++) {
    		Order order = new Order();
            order.setUserId(i);
            order.setOrderType(i % 2);
            order.setAddressId(i);
            order.setStatus("新增");
            orderRepository.insert(order);
            System.out.println(33333);
    	}
    	return 100;
    }
    
    /**
     * 新增数据
     * 
     * @return
     * @throws SQLException
     */
//    @Transactional
    public List<Long> insertData() throws SQLException {
        log.info("---------------------------- Insert Data ----------------------------");
        List<Long> result = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Order order = new Order();
            order.setUserId(i);
            order.setOrderType(i % 2);
            order.setAddressId(i);
            order.setStatus("新增");
            orderRepository.insert(order);
            
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getOrderId());
            orderItem.setUserId(i);
            orderItem.setPhone("1380000000" + i);
            orderItem.setStatus("新增");
            orderItemRepository.insert(orderItem);
            
            Address address = new Address();
            address.setAddressId((long) i);
            address.setAddressName("地址【" + i + "】");
            addressRepository.insert(address);
            
            result.add(order.getOrderId());
        }
        return result;
    }
    
    
    public void deleteData(final Long orderId) throws SQLException {
        log.info("---------------------------- Delete Data ----------------------------");
//        addressRepository.delete(1);
        orderRepository.delete(orderId);
        orderItemRepository.delete(orderId);
    }
    
    public void deleteData(final List<Long> orderIds) throws SQLException {
        log.info("---------------------------- Delete Data ----------------------------");
        long count = 1;
        for (Long each : orderIds) {
            orderRepository.delete(each);
            orderItemRepository.delete(each);
            addressRepository.delete(count++);
        }
    }
    
    public List<Order> selectAll() throws SQLException {
        List<Order> result = orderRepository.selectAll();
        return result;
    }
    
    public List<Order> selectJoin() throws SQLException {
        return  orderRepository.selectJoin();
    }
    
    public void cleanEnvironment() throws SQLException {
        orderRepository.dropTable();
        orderItemRepository.dropTable();
        addressRepository.dropTable();
    }
    
    /**
     * 更新地址
     * 
     * @param address
     * @return
     */
    public Address updateAddress(Address address) {
    	addressRepository.update(address);
    	return addressRepository.selectById(address.getAddressId());
    }
}
