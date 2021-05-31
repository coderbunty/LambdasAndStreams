package com.wm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface Consumer<T> {

  void accept(T t);

}

class Number<T> {

  List<T> numbers;

  public Number() {
    numbers = new ArrayList<>();
  }

  public void add(T num) {
    numbers.add(num);
  }

  public void remove(T num) {
    numbers.remove(num);
  }

  /*
   * for each number, execute the function being received in consumer object
   */
  public void foreach(Consumer<T> consumer) {
    for (T t : numbers) {
      consumer.accept(t);
    }
  }

  /*
   * just execute this runnable function on this object
   */
  public void executeFunction(Runnable runnable) {
    runnable.run();
  }

}

public class NumberTest {

  public static void main(String[] args) {
    Number<Integer> numbers = new Number<>();
    numbers.add(1);
    numbers.add(2);
    numbers.add(3);
    numbers.add(4);
    numbers.add(5);
    numbers.foreach(n -> System.out.println(n));

    Runnable runnable = () -> System.out.println("This is a class for adding Number of any type.");
    numbers.executeFunction(runnable);

    List<Integer> list = Arrays.asList(new Integer[]{1, 2, 3, 4, 5});
    list.forEach(System.out::print);
  }

}

