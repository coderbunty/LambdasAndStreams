package com.wm;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Lambdas {

    public static void main(String[] args) {
        // creating a thread using lambda expression
        Thread task1 = new Thread(() -> {
            System.out.println("Inside child thread1..");
        });

        Runnable runner = () -> System.out.println("Inside child thread2");
        Thread task2 = new Thread(runner);

        // using lambda expression with Collections forEach()
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        /*
         forEach will pass each element from numbers collection to an object
         of a class which implements Consumer interface. accept() in Consumer
         interface decides what needs to be done with each element.
        */
        numbers.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer num) {
                System.out.println(num);
            }
        });

        //numbers.forEach((Integer val) -> System.out.println(val));
        numbers.forEach(val -> System.out.println(val));
    }
}
