//package com.config.server;
//
//import org.springframework.stereotype.Service;
//
//@Service
//	public class OrderService {
//
//	    private final UserClient userClient;
//
//	    public OrderService(UserClient userClient) {
//	        this.userClient = userClient;
//	    }
//
//	    public void createOrder(Long userId, Order order) {
//	        UserDTO user = userClient.getUserById(userId);
//	        // use user info to validate or set shipping details
//	        System.out.println("Placing order for: " + user.getName());
//	    }
//	}
//
//
