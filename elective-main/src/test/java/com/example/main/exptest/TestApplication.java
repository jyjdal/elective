package com.example.main.exptest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@DisplayName("应用类，因为已经有了一个主类，所以这个类以测试的形式呈现")
public class TestApplication {
    @DisplayName("应用开始前准备数据")
    @BeforeAll
    public static void initData() {
        Courses.cList.add(new Course(1, "Java", 0, 60, "a"));
        Courses.cList.add(new Course(2, "Web", 0, 58, "b"));
        Courses.cList.add(new Course(3, "Python", 0, 120, "c"));
        Courses.cList.add(new Course(4, "Com", 1, 66, "d"));
        Courses.cList.add(new Course(5, "DB", 1, 122, "e"));
        Courses.cList.add(new Course(6, "Net", 1, 130, "f"));

        Users.tList.add(new Teacher("a", "123", 1, "教授"));
        Users.tList.add(new Teacher("b", "132", 2, "副教授"));
        Users.tList.add(new Teacher("c", "231", 3, "副教授"));
        Users.tList.add(new Teacher("d", "213", 4, "讲师"));
        Users.tList.add(new Teacher("e", "312", 5, "讲师"));
        Users.tList.add(new Teacher("f", "321", 6, "讲师"));

        Users.sList.add(new Student("g", "456", 11, "计算机1901"));
        Users.sList.add(new Student("h", "465", 12, "计算机1902"));
        Users.sList.add(new Student("i", "546", 13, "计算机1903"));
        Users.sList.add(new Student("j", "564", 14, "计算机1904"));
        Users.sList.add(new Student("k", "645", 15, "计算机1905"));
        Users.sList.add(new Student("l", "654", 16, "计算机1906"));
    }

    public static void menu() {
        System.out.println("请输入管理员密码：");
        Scanner scanner = new Scanner(System.in);
        String pass = scanner.next();
        if (Users.admin.pass.equals(pass)) {
            while (true) {
                System.out.printf("请选择操作：%s  %s  %s  %s  %s  %s  %s  %s  %s%n",
                        "1.新建课程.", "2.删除课程.", "3.设置课程教师.",
                        "4.查找老师讲课课程.", "5.显示课程列表.",
                        "6.按照选课人数进行排序.", "7.显示学生列表.",
                        "8.显示教师列表.", "0.退出.");
                int i = scanner.nextInt();
                if (i == 0) {
                    break;
                } else if (i < 1 || i > 8) {
                    System.out.println("输入错误！");
                } else {
                    switch (i) {
                        case 1 -> Courses.addCourses();
                        case 2 -> Courses.deleteCourse();
                        case 3 -> Courses.setCourseTeacher();
                        case 7 -> Users.showStudents();
                        case 8 -> Users.showTeachers();
                        case 4 -> Courses.searchCourseByTeacher();
                        case 5 -> Courses.showCourses();
                        case 6 -> Courses.sortCourseList();
                    }
                }
            }
        } else {
            System.out.println("密码错误！");
        }
        scanner.close();
    }

    @DisplayName("这里作为应用程序的入口")
    @Test
    public void main() {
        TestApplication.menu();
    }
}
