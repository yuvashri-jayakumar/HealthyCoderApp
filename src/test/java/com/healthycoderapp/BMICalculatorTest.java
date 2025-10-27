package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BMICalculatorTest {

    private String environment = "dev";

    @BeforeAll
    static void beforeAllTests(){
        System.out.println("Setup db connections");
    }

    @AfterAll
    static void afterAllTests(){
        System.out.println("Closing Connections");
    }

    @Nested
    @DisplayName("DietRecommendedTests")
    class DietRecommendedInnerclassTests{
        @Test
        @DisplayName("DietRecommendedTest1")
        void should_ReturnTrue_When_DietRecommended() {
            //given
            double weight = 63.5;
            double height = 1.54;
            //when
            boolean recommended = BMICalculator.isDietRecommended(weight,height);
            //then
            assertTrue(recommended);
        }
        @Test
        @Disabled
        void should_ReturnFalse_When_DietNotRecommended() {
            //given
            double weight = 55;
            double height = 1.54;
            //when
            boolean recommended = BMICalculator.isDietRecommended(weight,height);
            //then
            assertFalse(recommended);
        }

        @Test
        void should_ThrowException_When_HeightZero() {
            //given
            double weight = 55;
            double height = 0.0;
            //when
            Executable executable = () -> BMICalculator.isDietRecommended(weight,height);
            //then
            assertThrows(ArithmeticException.class, executable);
        }

        @ParameterizedTest
        @ValueSource(doubles = {63.5,88.2,90.1})
        void should_ReturnTrue_When_DietRecommended_MultiWeightInputs(Double coderWeight) {
            //given
            double weight = coderWeight;
            double height = 1.54;
            //when
            boolean recommended = BMICalculator.isDietRecommended(weight,height);
            //then
            assertTrue(recommended);
        }

        @ParameterizedTest(name = "weight={0},height={1}")
        @CsvSource(value = {"63.5,1.54","88.2,1.6","90.1,1.7"})
        void should_ReturnTrue_When_DietRecommended_MultiInputs(Double coderWeight,Double coderHeight) {
            //given
            double weight = coderWeight;
            double height = coderHeight;
            //when
            boolean recommended = BMICalculator.isDietRecommended(weight,height);
            //then
            assertTrue(recommended);
        }

        @ParameterizedTest(name = "weight={0},height={1}")
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void should_ReturnTrue_When_DietRecommended_FileInput(Double coderWeight,Double coderHeight) {
            //given
            double weight = coderWeight;
            double height = coderHeight;
            //when
            boolean recommended = BMICalculator.isDietRecommended(weight,height);
            //then
            assertTrue(recommended);
        }
    }

    @Nested
    class CodersWithWorstBMITests{
        @Test
        void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty(){
            //given
            List<Coder> coderList = new ArrayList<>();
            coderList.add(new Coder(1.6,58));
            coderList.add(new Coder(1.82,90));
            coderList.add(new Coder(1.82, 60));
            //when
            Coder actual =  BMICalculator.findCoderWithWorstBMI(coderList);
            //then
            assertAll(
                    () ->assertEquals(1.82, actual.getHeight()),
                    () -> assertEquals(90, actual.getWeight())
            );
            //assertEquals(1.5, actual.getHeight());
            //assertEquals(70, actual.getWeight());
        }

        @Test
        void should_ReturnCoderWithWorstBMI_When_CoderListEmpty(){
            //given
            List<Coder> coderList = new ArrayList<>();
            //when
            Coder actual =  BMICalculator.findCoderWithWorstBMI(coderList);
            //then
            assertNull(actual);
        }

        @Test
        void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty_inMs() {
            Assumptions.assumeTrue(BMICalculatorTest.this.environment.equals("prod")); //assume will skip the test case if the condition fails.
            //given
            List<Coder> coderList = new ArrayList<>();
            for (int i=0;i<=10000;i++) {
                Coder coder = new Coder(1.0+i,40+i);
                coderList.add(coder);
            }

            //when
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coderList);
            //then
            assertTimeout(Duration.ofMillis(5), executable);
        }
    }

    @Nested
    class GetBMIScoresTests{
        @Test
        void should_ReturnArrayBMIScores_When_CoderListNotEmpty(){
            //given
            List<Coder> coderList = new ArrayList<>();
            coderList.add(new Coder(1.6,58));
            coderList.add(new Coder(1.82,90));
            coderList.add(new Coder(1.82, 60));
            double[] expected = {22.66,27.17,18.11};
            //when
            double[] actual =  BMICalculator.getBMIScores(coderList);
            //Arrays.stream(actual).forEach(System.out::println);
            //then

            assertArrayEquals(expected,actual);
        }
    }






}