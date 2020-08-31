package com.wm;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.wm.Person.*;

public class Streams {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        System.out.println(
            numbers.stream()
                    .filter(e -> e % 2 == 0)    // filters out odd numbers from the stream
                    .map(e -> e * 2.0)          // maps input integer stream to output double stream with values doubled
                    .reduce(0.0, new BinaryOperator<Double>() {
                        @Override
                        public Double apply(Double aDouble, Double aDouble2) {
                            return aDouble + aDouble2;
                        }
                    })                          // reduces the stream to a value, here adds all the elements of the stream
        );

        System.out.println(
                numbers.stream()
                        .filter(e -> e % 2 == 0)
                        .map(e -> e * 2.0)
                        .reduce(0.0, (carry, e) -> carry + e));

        System.out.println(
                numbers.stream()
                        .filter(e -> e % 2 == 0)
                        .mapToDouble(e -> e * 2.0)
                        .sum());                        // sum() is a reduce operation

        System.out.println(
                numbers.stream()
                        .filter(e -> e % 2 == 0)
                        .filter(e -> e > 3)
                        .map(e -> e * 2)
                        .findFirst()
                        .orElse(null)
        );


        // Stream creation
        // 1. from an existing array
        System.out.println(
                Stream.of(new Integer[]{10, 20, 30, 40, 50})
                        .count()
        );

        // 2. from a Collection of list
        System.out.println(
                Arrays.asList("Bob", "the", "builder").stream()
                                                        .findAny()
                                                        .orElse(null)
        );

        // 3. using stream builder
        Stream.Builder<String> strStreamBuilder = Stream.builder();
        strStreamBuilder.accept("one");
        strStreamBuilder.accept("two");
        Stream<String> strStream = strStreamBuilder.build();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Different stream operations

        // 1. forEach() - its a terminal operation, takes object of Consumer<T> functional interface
        //                calls the supplied function on each element
        //                forEach doesn't return anything, void.
        Integer[] intArr = new Integer[]{5, 4, 3, 2, 1};
        //Stream.of(intArr).forEach(e -> e * 2);    // cannot return anything, invalid
        Stream.of(intArr).forEach(System.out::print);

        // 2. map - it is an intermediate operation, it produces a new stream
        //          it uses Function<T, R> - T is the input type, R is the output type after mapping
        Stream.of(intArr)
                      .map(String::valueOf)
                      .map(e -> Integer.valueOf(e))
                      .sorted()
                      .forEach(System.out::print);


        // 3. filter - intermediate operation, returns a stream, filters out elements
        //             uses Predicate<T> functional interface, the abstract function of which return a boolean
        numbers.stream()
               .filter(e -> e % 2 == 0)
               .forEach(System.out::print);


        // 4. collect() - terminal operation, used to get stuff out of the stream once our operations are done
        //                'Collectors' object to be passed to collect in a specific data structure
        Set<Integer> resSet = numbers.stream()
                                     .filter(e -> e % 3 == 0)
                                     .collect(Collectors.toSet());


        // 5. findFirst() - returns an 'Optional' object for the first entry in the stream, Optional can also be empty
        System.out.println(
                numbers.stream()
                       .findFirst()
                     //.get()
                       .orElse(null)
        );

        // converting stream to array
        Integer[] arr1 = numbers.stream().toArray(Integer[]::new);


        // reduce() - reduction & terminal operation, takes a sequence of input elements & combines them into one
        //            by the repeated application of a combining operation.
        //            "T reduce(T identity, BinaryOperator<T> accumulator)"
        //            identity is the identity value for respective operations & accumulator is the binary operation we repeatedly perform.
        //             For eg, identity value for addition is 0 & for multiplication is 1.
        System.out.println(
                numbers.stream()
                        .filter(e -> e % 2 == 0)
                        .map(e -> e * 2.0)
                        .reduce(0.0, (carry, e) -> carry + e)
        );

        System.out.println(
                numbers.stream()
                        .reduce(0, Integer::sum)
        );

        // sum(), min(), max(), findFirst(), findAny() etc are all reduce operations

        // 6. groupingBy() - group by certain parameter
        List<Person> people = Arrays.asList(
                new Person("Sara", Sex.FEMALE, 20),
                new Person("Sara", Sex.FEMALE, 22),
                new Person("Bob", Sex.MALE, 20),
                new Person("Paula", Sex.FEMALE, 32),
                new Person("Paul", Sex.MALE, 32),
                new Person("Jack", Sex.MALE, 2),
                new Person("Jack", Sex.MALE, 72),
                new Person("Jill", Sex.FEMALE, 12)
        );
        // given a list of people, create a map where their name is the key & value is all the people with same name
        Map<String, List<Person>> personNameMap = people.stream()
                                                        .collect(Collectors.groupingBy(Person::getName));

        // given a list of people, create a map where their name is the key & value is ages of people of that name
        Map<String, List<Integer>> peopleNameAgeMap =
            people.stream()
                  .collect(Collectors.groupingBy(
                            e -> e.getName(), Collectors.mapping(Person::getAge, Collectors.toList()))
                  );

        // other important stream functions - distinct, limit, skip, sorted

        // characteristics of a stream - sized, ordered, distinct, sorted

        // 7. Infinite streams
        Stream.iterate(100, e -> e + 1);
        // Given a number 'k', & a count 'n', find the total of double of 'n' even numbers starting from 'k',
        // where sqrt of each number is > 20.
        int k = 121;
        int n = 51;
        System.out.println(
                Stream.iterate(k, e -> e + 1)
                      .filter(e -> e % 2 == 0)
                      .filter(e -> Math.sqrt(e) > 20)
                      .mapToInt(e -> 2 * e)
                      .limit(n)
                      .sum()
        );
    }
}
