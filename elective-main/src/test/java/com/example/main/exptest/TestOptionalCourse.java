package com.example.main.exptest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("选修课测试类")
public class TestOptionalCourse {
    @DisplayName("选修课测试")
    @Test
    public void testNewCourse() {
        OptionalCourse course = new OptionalCourse(1, "Java", 2, 60, "liu", 100);
        course.show();
    }
}
