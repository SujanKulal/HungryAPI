package com.hungryapi.controller;

import com.hungryapi.entity.Dish;
import com.hungryapi.repository.DishRepository;
import com.hungryapi.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish){
        return new ResponseEntity<>(dishService.addDish(dish), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Dish>> getAllDishes(){
        return new ResponseEntity<>(dishService.getAllDishes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable Long id){
        return new ResponseEntity<>(dishService.getDishById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dish> updateDish(@PathVariable Long id, @RequestBody Dish updateddish){
        return new ResponseEntity<>(dishService.updateDish(id,updateddish),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id){
        dishService.deleteDish(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
