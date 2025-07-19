package com.hungryapi.controller;

import com.hungryapi.entity.Dish;
import com.hungryapi.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    @Autowired
    private DishRepository dishRepository;

    @PostMapping
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish){
        return new ResponseEntity<>(dishRepository.save(dish), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes(){
        return new ResponseEntity<>(dishRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable Long id){
        return new ResponseEntity<>(dishRepository.findById(id).orElseThrow(()-> new RuntimeException("Dish not found with id: " + id)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable Long id, @RequestBody Dish updateddish){
        Dish dish = dishRepository.findById(id).orElseThrow(()-> new RuntimeException("Dish not found"));
        dish.setName(updateddish.getName());
        dish.setPrice(updateddish.getPrice());
        dish.setCategory(updateddish.getCategory());
        dish.setAvailable(updateddish.isAvailable());
        return new ResponseEntity<>(dishRepository.save(dish), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id){
        if(dishRepository.existsById(id)){
            dishRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
