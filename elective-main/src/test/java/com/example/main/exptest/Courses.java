package com.example.main.exptest;

import java.io.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class Courses {
    public static Vector<Course> cList;

    static {
        // 按照实验的规模来看，10个课程足够
        cList = new Vector<>(10);
    }

    public static void addCourse() {
        if (cList.size() < 1) {
            // 课程列表长度为0时，新创建的课程号为1
            Courses.cList.add(Course.inputCourse(1));
        } else {
            // 长度不为0时，新创建的课程号为现有的最大编号+1
            Courses.cList.add(Course.inputCourse(cList.size() + 1));
        }
    }

    public static void addCourses() {
        int i = 1;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("请输入第" + (i++) + "门课程信息");
            Courses.addCourse();
            System.out.println("是否继续添加课程？y/n");
        } while ("y".equals(scanner.next()));
        scanner.close();
    }

    public static void showCourses() {
        System.out.println("编号   课程   类型   教师   选课人数");
        for (Course course : cList) {
            course.show();
        }
    }

    public static void sortCourseList() {
        Collections.sort(cList);
    }

    public static void searchCourseByTeacher() {
        System.out.println("请输入需要查找的教师名称：");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.next();
        for (Course course : cList) {
            if (course.teacher.equals(name)) {
                course.show();
            }
        }
    }

    public static void deleteCourse() {
        System.out.println("请输入课程号：");
        Scanner scanner = new Scanner(System.in);
        int cNum = scanner.nextInt();
        scanner.close();
        boolean found = false;
        for (Course course : cList) {
            if (course.id == cNum) {
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("没有找到课程！");
            return;
        }
        Iterator<Course> iterator = cList.iterator();

        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (course.id == cNum) {
                cList.remove(course);
                break;
            }
        }
    }

    public static void setCourseTeacher() {
        System.out.println("请输入课程号：");
        Scanner scanner = new Scanner(System.in);
        int cNum = scanner.nextInt();
        boolean found = false;
        for (Course course : cList) {
            if (course.id == cNum) {
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("没有找到课程！");
            return;
        }
        System.out.println("请输入新的教师名称：");
        String teacher = scanner.next();
        scanner.close();
        for (Course course : cList) {
            if (course.id == cNum) {
                int index = cList.indexOf(course);
                course.teacher = teacher;
                cList.set(index, course);
                break;
            }
        }
    }

    public static void saveCourses() {
        File file = new File("E:/Courses.txt");
        try {
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            }
            BufferedWriter out = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            for (Course course : cList) {
                out.write(course.toString() + "\r\n");
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readCourses() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("E:/Courses.txt")));
            String data;
            while ((data = in.readLine()) != null) {
                Scanner scanner = new Scanner(data);
                int id = scanner.nextInt();
                String name = scanner.next();
                int type = scanner.nextInt();
                int stuNum = scanner.nextInt();
                String teacher = scanner.next();
                if (type == 0) {
                    int credit = scanner.nextInt();
                    cList.add(new RequiredCourse(id, name, type, stuNum, teacher, credit));
                } else {
                    int maxStuNum = scanner.nextInt();
                    cList.add(new OptionalCourse(id, name, type, stuNum, teacher, maxStuNum));
                }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
