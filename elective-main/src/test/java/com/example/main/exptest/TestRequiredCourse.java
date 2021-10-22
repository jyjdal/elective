package com.example.main.exptest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("必修课测试类")
public class TestRequiredCourse {
    @DisplayName("必修课测试")
    @Test
    public void testNewCourse() {
        RequiredCourse course = new RequiredCourse(1, "Java", 0, 60, "liu", 3);
        course.show();
    }
}
