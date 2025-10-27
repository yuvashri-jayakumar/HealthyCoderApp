package com.healthycoderapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DietPlannerTest {

    private DietPlanner dietPlanner;
    @BeforeEach
    void setUp() {
        this.dietPlanner = new DietPlanner(40,25,35);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Unit test case executed");
    }

    @Test
    void should_ReturnCorrectPlan_when_calculateDietCalled() {
        //given
        Coder coder = new Coder(1.6,80,32, Gender.FEMALE);
        DietPlan expected = new DietPlan(1892, 189, 53, 166);
        //when
        DietPlan actual = dietPlanner.calculateDiet(coder);
        //System.out.println(actual);
        //then
       // assertEquals(expected,actual); --compares the objects in the memory reference not the values
        assertAll(
                ()->assertEquals(expected.getCalories(),actual.getCalories()),
                ()->assertEquals(expected.getCarbohydrate(),actual.getCarbohydrate()),
                ()->assertEquals(expected.getFat(),actual.getFat()),
                ()->assertEquals(expected.getProtein(),actual.getProtein())
        );
    }

    @RepeatedTest(value = 5,name = RepeatedTest.SHORT_DISPLAY_NAME)
    void should_ReturnCorrectPlan_when_calculateDietCalled_ManyTimes() {
        //given
        Coder coder = new Coder(1.6,80,32, Gender.FEMALE);
        DietPlan expected = new DietPlan(1892, 189, 53, 166);
        //when
        DietPlan actual = dietPlanner.calculateDiet(coder);
        //System.out.println(actual);
        //then
        // assertEquals(expected,actual); --compares the objects in the memory reference not the values
        assertAll(
                ()->assertEquals(expected.getCalories(),actual.getCalories()),
                ()->assertEquals(expected.getCarbohydrate(),actual.getCarbohydrate()),
                ()->assertEquals(expected.getFat(),actual.getFat()),
                ()->assertEquals(expected.getProtein(),actual.getProtein())
        );
    }


}