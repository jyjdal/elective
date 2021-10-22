package com.example.main.exptest;

import java.util.Scanner;

public class Student extends User {
    // 学号
    public int id;
    // 班级
    public String Class;

    public Student() {
    }

    public Student(String name, String pass, int id, String Class) {
        this.name = name;
        this.setPass(pass);
        this.id = id;
        this.Class = Class;
    }

    @Override
    public void show() {
        System.out.println(id + " " + name + " " + Class);
    }

    // 学生登录方法
    @Override
    public int login() {
        System.out.println("请输入学号：");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        System.out.println("请输入密码：");
        String pass = scanner.next();
        scanner.close();
        if (this.id == id && this.getPass().equals(pass)) {
            System.out.println(Class + " " + name + " 你好！");
            return 1;
        } else {
            return 0;
        }
    }
}
