package com.example.main.exptest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

@DisplayName("面向对象入门")
public class TestCourse {
    @AfterEach
    public void newLine() {
        System.out.println();
    }

    @DisplayName("课程信息类")
    @Test
    public void testCourse() {
        Course course = new Course();
        course.id = 1;
        course.name = "java 面向对象编程";
        course.type = 0;
        course.show();
    }

    @DisplayName("从输入中获取课程信息")
    @Test
    public void testCourseInput() {
        Course course = new Course();
        Scanner scanner = new Scanner(System.in);
        course.id = scanner.nextInt();
        course.name = scanner.next();
        course.type = scanner.nextInt();
        scanner.close();
        course.show();
    }

    @DisplayName("全参数构造函数")
    @Test
    public void testAllArgsConstructor() {
        Course course = new Course(1, "Java程序设计", 2);
        course.show();
    }

    @DisplayName("while循环")
    @Test
    public void testForLoop() {
        Course c = new Course();
        c.id = 1;
        c.name = "Java 面向对象编程";
        c.type = 1;
        Course c1 = new Course(2, "Java程序设计", 0);
        Course c2 = new Course(3, "编译原理", 2);
        Vector<Course> cList = new Vector<>();
        cList.add(c);
        cList.add(c1);
        cList.add(c2);
        Iterator<Course> iterator = cList.iterator();
        do {
            iterator.next().show();
        } while (iterator.hasNext());
    }

    @DisplayName("新的Course类")
    @Test
    public void testNewCourse() {
        Course course = new Course(1, "Java程序设计", 0, 60, "liu");
        System.out.println(course.id + " " + course.name + " " + course.type);
        System.out.println(course.stuNum + " " + course.teacher);
    }
}
