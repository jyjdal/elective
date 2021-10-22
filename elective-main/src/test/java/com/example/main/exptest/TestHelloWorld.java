package com.example.main.exptest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@DisplayName("Hello world相关内容")
public class TestHelloWorld {
    @AfterEach
    public void newLine() {
        System.out.println();
    }

    @DisplayName("第一个Hello world")
    @Test
    public void testHelloWorld1() {
        System.out.println("Hello world!");
    }

    @DisplayName("带有输入的Hello world")
    @Test
    public void testHelloWorld2() {
        System.out.println("请输入您的姓名：");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        scanner.close();
        System.out.printf("Hello world %s%n", name);
    }
}
