package com.example.main.exptest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("教师测试类")
public class TestTeacher {
    @AfterEach
    public void newLine() {
        System.out.println();
    }

    @DisplayName("教师类")
    @Test
    public void testTeacher() {
        Teacher teacher = new Teacher();
        teacher.workId = 20194780;
        teacher.name = "孙强";
        teacher.level = "教授";
        teacher.show();
    }

    @DisplayName("全参构造函数")
    @Test
    public void testAllArgsConstructor() {
        Teacher teacher = new Teacher("孙强", "123456", 20194780, "教授");
        teacher.show();
    }

    @DisplayName("教师登录")
    @Test
    public void testLoginAsTeacher() {
        Teacher teacher = new Teacher("孙强", "123456", 20194780, "教授");
        System.out.println(teacher.login());
    }
}
