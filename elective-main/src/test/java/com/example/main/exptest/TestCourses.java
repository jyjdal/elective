package com.example.main.exptest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Courses类")
public class TestCourses {
    private Courses courses;

    @AfterEach
    public void newLine() {
        System.out.println();
    }

//    @BeforeAll
//    public static void initData() {
//        Courses.cList = new Vector<>();
//        Courses.cList.add(new Course(1, "java", 0, 60, "a"));
//        Courses.cList.add(new OptionalCourse(2, "web", 1, 58, "b", 120));
//        Courses.cList.add(new RequiredCourse(3, "db", 1, 120, "c", 4));
//    }

    @DisplayName("新增课程信息")
    @Test
    public void testNewClasses() {
        Courses.addCourses();
        System.out.println(Courses.cList.size());
    }

    @DisplayName("排序并显示课程信息")
    @Test
    public void sortAndDisplay() {
        Courses.addCourses();
        Courses.sortCourseList();
        Courses.showCourses();
    }

    @DisplayName("按照教师名称查找")
    @Test
    public void testSearchByTeacher() {
        Courses.searchCourseByTeacher();
    }

    @DisplayName("写入文件")
    @Test
    public void testWriteToFile() {
        Courses.saveCourses();
    }

    @DisplayName("读取文件")
    @Test
    public void testReadFromFile() {
        Courses.readCourses();
        System.out.println(Courses.cList.size());
    }
}
