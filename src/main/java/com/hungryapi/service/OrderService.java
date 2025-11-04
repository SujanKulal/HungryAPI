package com.hungryapi.service;

import com.hungryapi.dto.OrderRequestDTO;
import com.hungryapi.dto.OrderResponseDTO;
import com.hungryapi.entity.Customer;
import com.hungryapi.entity.Dish;
import com.hungryapi.entity.Order;
import com.hungryapi.exceptions.OrderNotFoundException;
import com.hungryapi.repository.CustomerRepository;
import com.hungryapi.repository.DishRepository;
import com.hungryapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private OrderRepository orderRepository;

    public OrderResponseDTO placeOrder(OrderRequestDTO request){

        //Get customer by id

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(()-> new RuntimeException("Customer not found"));


        //Get dishes

        List<Dish> dishes = dishRepository.findAllById(request.getDishIds());

        //Creating Order Entity

        Order order = new Order();
        order.setCustomer(customer);
        order.setDishes(dishes);
        order.setTotalAmount(dishes.stream().mapToDouble(Dish::getPrice).sum());
        order.setOrderTime(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);


        //map to response DTO

        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(savedOrder.getId());
        response.setCustomerName(customer.getName());
        response.setDishNames(dishes.stream().map(Dish::getName).collect(Collectors.toList()));
        response.setTotalAmount(savedOrder.getTotalAmount());
        response.setTimestamp(savedOrder.getOrderTime());

        return response;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException("Order Not found with id " + id));
    }
}
