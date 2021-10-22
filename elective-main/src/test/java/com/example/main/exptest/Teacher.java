package com.example.main.exptest;

import java.util.Scanner;

public class Teacher extends User {
    // 工号
    public int workId;

    // 职称：教授、副教授、讲师
    public String level;

    public Teacher() {
    }

    public Teacher(String name, String pass, int workId, String level) {
        this.name = name;
        this.setPass(pass);
        this.workId = workId;
        this.level = level;
    }

    @Override
    public void show() {
        System.out.println(workId + " " + name);
    }

    // 教师登录方法
    @Override
    public int login() {
        System.out.println("请输入工号：");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        System.out.println("请输入密码：");
        String pass = scanner.next();
        scanner.close();
        if (this.workId == id && this.getPass().equals(pass)) {
            System.out.println(name + " " + level + " " + "您好！");
            return 1;
        } else {
            return 0;
        }
    }
}
