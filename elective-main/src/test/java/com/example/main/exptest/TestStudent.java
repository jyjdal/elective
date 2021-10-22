package com.example.main.exptest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("学生测试类")
public class TestStudent {
    @AfterEach
    public void newLine() {
        System.out.println();
    }

    @DisplayName("学生类")
    @Test
    public void testStudent() {
        Student s = new Student();
        s.name = "孙强";
        s.id = 20194780;
        s.Class = "计算机1906";
        s.show();
    }

    @DisplayName("全参构造函数")
    @Test
    public void testAllArgsConstructor() {
        Student student = new Student("孙强", "123456", 20194780, "计算机1906");
        student.show();
    }

    @DisplayName("学生登录")
    @Test
    public void testLoginAsStudent() {
        Student student = new Student("孙强", "123456", 20194780, "计1906");
        System.out.println(student.login());
    }
}
