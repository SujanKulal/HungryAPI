package com.hungryapi.service;

import com.hungryapi.entity.Dish;
import com.hungryapi.exceptions.DishNotFoundException;
import com.hungryapi.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;

    public List<Dish> getAllDishes(){
        return dishRepository.findAll();
    }

    public Dish getDishById(Long id){
        return dishRepository.findById(id).orElseThrow(()-> new DishNotFoundException("Dish with " + id + " not found."));
    }

    public Dish addDish(Dish dish){
        return dishRepository.save(dish);
    }

    public Dish updateDish(Long id,Dish updateDish){
        Dish dish = dishRepository.findById(id).orElseThrow(()-> new DishNotFoundException("Not found dish with id: " + id));
        dish.setName(updateDish.getName());
        dish.setCategory(updateDish.getCategory());
        dish.setPrice(updateDish.getPrice());
        return dishRepository.save(dish);
    }

    public void deleteDish(Long id){
        if(dishRepository.existsById(id)){
            dishRepository.deleteById(id);
        }else throw new RuntimeException("Id cannot be found");
    }
}
