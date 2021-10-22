package com.example.main.exptest;

import java.util.Scanner;

public class Course implements Comparable<Course> {
    // 选课学生人数
    public int stuNum;
    // 授课教师
    public String teacher;
    // 课程编号
    int id;
    // 课程名称
    String name;
    // 0为必修，1为选修
    int type;

    // 全参数构造方法
    public Course(int id, String name, int type, int stuNum, String teacher) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.stuNum = stuNum;
        this.teacher = teacher;
    }

    public Course() {
    }

    public Course(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public static Course inputCourse(int id) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入课程名：");
        String name = scanner.next();
        System.out.println("请输入课程类型（0必修，1选修）：");
        int type = scanner.nextInt();
        System.out.println("请输入授课教师：");
        String teacher = scanner.next();
        System.out.println("请输入选课人数");
        int stuNum = scanner.nextInt();
        Course course;
        if (type == 0) {
            System.out.println("请输入学分：");
            int credit = scanner.nextInt();
            return new RequiredCourse(id, name, type, stuNum, teacher, credit);
        } else {
            System.out.println("请输入最大选课人数：");
            int maxStuNum = scanner.nextInt();
            return new OptionalCourse(id, name, type, stuNum, teacher, maxStuNum);
        }
    }

    // 显示课程信息的方法
    public void show() {
        System.out.printf("%d   %s   %d   %s   %d%n", id, name, type, teacher, stuNum);
    }

    @Override
    public int compareTo(Course o) {
        return this.stuNum - o.stuNum;
    }

    @Override
    public String toString() {
        return id + "  " + name + "  " + type + "  " + stuNum + "  " + teacher;
    }
}
