package com.hungryapi.controller;

import com.hungryapi.entity.Dish;
import com.hungryapi.entity.Order;
import com.hungryapi.repository.OrderRepository;
import com.hungryapi.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.*;

@Controller
public class WebController {

    @Autowired
    private DishService dishService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/home")
    public String homePage(Model model) {
        List<Dish> dishes = dishService.getAllDishes();
        model.addAttribute("dishes", dishes);
        return "home";
    }

    @PostMapping("/order-summary")
    public String orderSummary(
            @RequestParam("dishIds") List<Long> dishIds,
            @RequestParam Map<String,String> allFormsData,
            Model model) {

        // Get selected dishes in the order they appear
        List<Dish> selectedDishes = new ArrayList<>();
        List<Integer> selectedQuantities = new ArrayList<>();

        for(Long dishId : dishIds){
            Dish dish = dishService.getDishById(dishId);
            selectedDishes.add(dish);

            //get quantities
            String quantityFieldName = "quantity_" + dishId;
            String quantityValuesAsText = allFormsData.get(quantityFieldName);

            int quantity = Integer.parseInt(quantityValuesAsText);
            selectedQuantities.add(quantity);
        }



        double total = 0;

        for(int i=0;i<selectedDishes.size();i++){
            double dishPrice = selectedDishes.get(i).getPrice();
            int dishQuantity = selectedQuantities.get(i);
            total+=dishQuantity*dishPrice;

        }

        total = Math.round(total*100)/100;

        model.addAttribute("selectedDishes", selectedDishes);
        model.addAttribute("quantities", selectedQuantities);
        model.addAttribute("total", total);

        return "order-summary";
    }

    @PostMapping("/confirm-order")
    public String confirmOrder(
            @RequestParam("dishIds") List<Long> dishIds,
            @RequestParam("quantities") List<Integer> quantities,
            @RequestParam("total") double total,
            Model model) {

        List<Dish> orderedDishes = new ArrayList<>();
        for(Long  dishId : dishIds){
            Dish dish = dishService.getDishById(dishId);
            orderedDishes.add(dish);
        }

        Order order = new Order();
        order.setDishes(orderedDishes);
        order.setTotalAmount(total);
        order.setOrderTime(LocalDateTime.now());

        orderRepository.save(order);

        model.addAttribute("orderedDishes", orderedDishes);
        model.addAttribute("quantities", quantities);
        model.addAttribute("total", total);

        return "order-confirmation";
    }
}
