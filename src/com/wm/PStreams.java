package com.wm;

/*
 * In the imperative style the structure of sequential code is very different from the structure of concurrent code
 * When using streams, the structure of sequential code is same as the structure of concurrent code
 * (functional style of programming has less complexity in terms of coding & it is also easier to parallelize)
 */

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class PStreams {

    private static int transform(int num) {
        System.out.println("t: " + num + " -- " + Thread.currentThread());
        sleep(1000);    // some time consuming opertion
        return num * 1;
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void print(int num) {
        System.out.println("p: " + num + " -- " + Thread.currentThread());
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        /*// sequential execution
        numbers.stream()
               .map(e -> transform(e))
               .forEach(System.out::println);*/

        // parallel execution
        numbers.parallelStream()
                .map(e -> transform(e))
                .forEach(e -> print(e));

        /*// one other way to execute a stream operation in parallel, when we just have a stream reference
        Stream<Integer> integerStream = numbers.stream();
        integerStream
                .parallel()
                .map(e -> transform(e))
                .forEach(System.out::println);*/

        System.out.println("---------------------------------------------------------------------------------");
        numbers.parallelStream()
                .map(e -> transform(e))
                .forEach(e -> print(e));
        /*
         * using forEachOrdered() ensures ordering if the stream guarantees ordering
         * here the stream is on a list which is a ordered DS
         * using forEachOrdered, parallelism is still there but in order
         * meaning, for eg 6th thread will not finish until 5th thread has finished executing
         * a static common thread pool, ForkJoinPool gets used by parallel streams to execute tasks in parallel
         * ForkJoinPool.commonPool()
         */
        System.out.println("Number of cores on the system: " + Runtime.getRuntime().availableProcessors());
        System.out.println(ForkJoinPool.commonPool());

        numbers.parallelStream()
                .map(e -> transform(e))
                .forEachOrdered(e -> print(e));


        // custom forkjoinpool
        List<String> names = Arrays.asList("bob", "martin", "sue", "kathy", "sara", "mike");
        Stream<String> stringStream = names.parallelStream()
                                           .map(String::toUpperCase);
        ForkJoinPool joinPool = new ForkJoinPool(100);
        joinPool.submit(() -> stringStream.forEach(System.out::println));   // submitting 6 runnable tasks to pool
        joinPool.shutdown();
        try {
            joinPool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
