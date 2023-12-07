package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	private static final Logger splunkLogger = LoggerFactory.getLogger("splunk.logger");
	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.error("Error during order submission");
			log.debug("Searching user repository with username ("+username+") returned null during submission.");
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);
		log.info("Submission successful for "+username+".");
		splunkLogger.info("Sale successful, amount = "+order.getTotal());
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.error("Error during order submission");
			log.debug("Searching user repository with username ("+username+") returned null during order retrieval.");
			return ResponseEntity.notFound().build();
		}
		log.info("orders for "+username+" returned successfully.");
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}
