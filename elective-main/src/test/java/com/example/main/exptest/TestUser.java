package com.example.main.exptest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Vector;

public class TestUser {
    @AfterEach
    public void newLine() {
        System.out.println();
    }

    @DisplayName("输入新密码")
    @Test
    public void testSetNewPassword() {
        User user = new User();
        user.name = "孙强";
        user.setPass("1234");
        user.show();
        user.setPassword();
    }

    @DisplayName("全参构造函数")
    @Test
    public void testAllArgsConstructor() {
        User user = new User("孙强", "123456");
        user.show();
    }

    @DisplayName("自动向上造型")
    @Test
    public void autoCast() {
        Vector<User> uList = new Vector<>();
        User admin = new User("admin", "123456");
        Student student = new Student("liu", "654321", 20180101, "计1801");
        Teacher teacher = new Teacher("ljl", "000000", 7480, "教授");
        uList.add(admin);
        uList.add(student);
        uList.add(teacher);
        for (User user : uList) {
            user.show();
        }
    }

    @DisplayName("管理员登录")
    @Test
    public void testLoginAsAdmin() {
        User user = new User("admin", "123456");
        System.out.println(user.login());
    }

    @DisplayName("封装性")
    @Test
    public void testEncapsulation() {
        User user = new User("admin", "123456");
        System.out.println(user.getPass());
        user.setPass("megumi");
        System.out.println(user.getPass());
    }
}
