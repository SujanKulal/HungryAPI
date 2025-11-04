package com.hungryapi.service;

import com.hungryapi.entity.Category;
import com.hungryapi.entity.Dish;
import com.hungryapi.repository.DishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {
    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    @Test  //Fake setup
    void testGetAllDishes(){
        List<Dish> fakeDishes = Arrays.asList(new Dish(1L, "Pizza", 200.9, Category.MAIN_COURSE),
                                                new Dish(2L, "Biriyani",300.0,Category.MAIN_COURSE)
        );
        when(dishRepository.findAll()).thenReturn(fakeDishes);

        //call real objects
        List<Dish> result = dishService.getAllDishes();

        assertEquals(2,result.size());
        assertEquals("Pizza", result.get(0).getName());
    }
}