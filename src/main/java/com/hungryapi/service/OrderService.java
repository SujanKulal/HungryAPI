package com.hungryapi.service;

import com.hungryapi.dto.OrderRequestDTO;
import com.hungryapi.entity.Customer;
import com.hungryapi.entity.Dish;
import com.hungryapi.entity.Order;
import com.hungryapi.repository.CustomerRepository;
import com.hungryapi.repository.DishRepository;
import com.hungryapi.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private OrderRepository orderRepository;

    public Order placeOrder(OrderRequestDTO request){

        //Get customer by id

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(()-> new RuntimeException("Customer not found with id: " + request.getCustomerId()));


        //Get dishes by dish ids

        List<Dish> selectedDishes = new ArrayList<>();

        for(Long dishId : request.getDishIds()){
            Dish dish = dishRepository.findById(dishId)
                    .orElseThrow(()-> new RuntimeException("Dish not found with ID: " + dishId));

            selectedDishes.add(dish);
        }


        //availability
        for(Dish dish : selectedDishes){
            if(!dish.isAvailable()){
                throw new RuntimeException("Dish " + dish.getName() + " is not available right now :(");
            }
        }

        //calculating total
        double total = 0;

        for(Dish dish : selectedDishes){
            total+=dish.getPrice();
        }

        total = Math.round(total*100) / 100.0;


       // creating order object

        Order order = new Order();
        order.setCustomer(customer);
        order.setDishes(selectedDishes);
        order.setTotalAmount(total);
        order.setOrderTime(LocalDateTime.now());

        //saving and order return

        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
