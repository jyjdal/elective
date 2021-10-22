package com.example.main.exptest;

import java.util.Vector;

public class Users {
    public static User admin;
    public static Vector<Teacher> tList;
    public static Vector<Student> sList;

    static {
        admin = new User("admin", "123456");

        // 按照实验的规模来看，10个大约就是需要的容量
        tList = new Vector<>(10);
        sList = new Vector<>(10);
    }

    public static void showStudents() {
        System.out.println("学号   姓名   班级");
        for (Student student : sList) {
            System.out.println(student.id + "  " + student.name + "  " + student.Class);
        }
    }

    public static void showTeachers() {
        System.out.println("工号   姓名   职级");
        for (Teacher teacher : tList) {
            System.out.println(teacher.workId + "  " + teacher.name + "  " + teacher.level);
        }
    }
}
