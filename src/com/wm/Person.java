package com.wm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Person {

    enum Sex{
        MALE, FEMALE
    }

    private String name;
    private LocalDate dob;
    private Sex gender;
    private int age;

    public Person(String name, LocalDate dob, Sex gender) {
        this.name = name;
        this.dob = dob;
        this.gender = gender;
    }

    public Person(String name, Sex gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Sex getGender() {
        return gender;
    }

    public int getAge() { return age; }

    public static int compareByAge(Person p1, Person p2) {
        return p1.getDob().compareTo(p2.getDob());
    }

    public static void main(String[] args) {
        Person person1 = new Person("abc", LocalDate.of(1989, 12, 3), Sex.MALE);
        Person person2 = new Person("stu", LocalDate.of(1991, 2, 21), Sex.FEMALE);

        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);

        // sorting using lambda expression in the place of a Comparator object
        Collections.sort(persons,
                (p1, p2) -> {
                    return p1.getDob().compareTo(p2.getDob());
                }
        );

        // using method reference, as Person::compareByAge perfectly fits in the place of what was expected
        // as part of object of Comparator functional interface
        Collections.sort(persons, Person::compareByAge);

    }
}
