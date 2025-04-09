package com.jceco.course.services;


import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jceco.course.entities.Order;
import com.jceco.course.entities.OrderItem;
import com.jceco.course.entities.Payment;
import com.jceco.course.repositories.OrderItemRepository;
import com.jceco.course.repositories.OrderRepository;
import com.jceco.course.services.exceptions.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	

	
	public List<Order> findAll(){
		
		return repository.findAll();
	}
	
	
	public Order findById(Long id) {
	    return repository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException(id));
	}

	
	
	
	public Order insert(Order order) {

	    Order savedOrder = repository.save(order);
	    

	    for (OrderItem item : order.getItems()) {
	        item.setOrder(savedOrder);
	        orderItemRepository.save(item); 
	    }
	    
	    savedOrder.setPayment(new Payment(null, Instant.now(), order));
	    savedOrder = repository.save(order);
	    
	    return savedOrder;
	}










	
}
