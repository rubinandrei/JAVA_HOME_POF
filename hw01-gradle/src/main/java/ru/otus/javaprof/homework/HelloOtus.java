package ru.otus.javaprof.homework;

import com.google.common.collect.Lists;

import java.util.List;

public class HelloOtus {
    public static void main(String[] args) {
        List<String> fruits = Lists.newArrayList("otus", "java", "Developer",
                "Professional", "gradle", "HelloOtus");

        fruits.forEach(x->{
            System.out.println(x.toUpperCase());
        });
    }
}
