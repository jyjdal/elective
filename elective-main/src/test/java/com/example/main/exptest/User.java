package com.example.main.exptest;

import java.util.Scanner;

public class User {
    // 用户名
    public String name;
    // 密码
    public String pass;

    public User() {
    }

    public User(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getPass() {
        return this.pass;
    }

    public void setPass(String pass) {
        if (pass.length() == 6) {
            this.pass = pass;
        }
    }

    // 显示用户信息
    public void show() {
        System.out.println("用户名：" + name);
    }

    // 修改用户密码
    public void setPassword() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请输入新密码（6位）：");
            String pass1 = scanner.next();
            System.out.println("请再次输入密码（6位）：");
            String pass2 = scanner.next();
            if (pass1.equals(pass2) && pass1.length() == 6) {
                this.pass = pass1;
                scanner.close();
                break;
            }
        }
    }

    // 管理员登录方法
    public int login() {
        System.out.println("请输入管理员密码：");
        Scanner scanner = new Scanner(System.in);
        if (this.pass.equals(scanner.next())) {
            scanner.close();
            return 1;
        } else {
            scanner.close();
            return 0;
        }
    }
}
