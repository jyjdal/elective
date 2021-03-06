package com.example.electivecourse.service;

import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecourse.dao.BaseCourseDAO;

import java.util.List;

/**
 * @author admin
 */
public interface CourseService {
    /**
     * 返回所有的课程信息
     *
     * @return 全部的课程信息
     */
    List<BaseCourseDAO> getAll();

    /**
     * 搜索是否有课程号为courseId的课程
     *
     * @param courseId 需要比较的课程号
     * @return 是否有对应课程号的课程
     */
    boolean hasCourse(String courseId);

    /**
     * 向课程列表中添加一个课程
     *
     * @param course 需要添加的课程
     * @return 添加课程的结果
     */
    ElectiveResult addCourse(BaseCourseDAO course);

    /**
     * 根据课程号删除对应课程
     *
     * @param courseId 需要删除课程的课程号
     * @return 删除课程的结果
     */
    ElectiveResult removeCourseByCourseId(String courseId);

    /**
     * 将课程按照选课人数进行排序
     *
     * @return 返回排序后的课程列表
     */
    List<BaseCourseDAO> getSortedByStuNum();

    /**
     * 为课程更改授课教师（的工号）
     *
     *
     * @param courseId 需要更改授课教师的课程的课程号
     * @param workId 需要更改的目标授课教师的工号
     * @return 更改结果
     */
    ElectiveResult setTeacherWordId(String courseId, String workId);

    /**
     * 根据教师工号查找教师教授的课程
     *
     * @param workId 需要搜索的教师工号
     * @return 所有符合条件的课程信息
     */
    List<BaseCourseDAO> getAllByTeacherWorkId(String workId);

    /**
     * 查找所有符合要求课程号的课程
     *
     * @param courseIds 需要查找的课程的集合
     * @return 查找到的所有课程
     */
    List<BaseCourseDAO> getAllByCourseIds(List<String> courseIds);

    /**
     * 课程端的选课操作
     *
     * @param courseId 被选择课程的课号
     * @return 课程是否选课成功
     */
    ElectiveResult selectCourse(String courseId);

    /**
     * 课程端的退课操作
     *
     * @param courseId 需要退课的课程课号
     * @return 退课的结果
     */
    ElectiveResult deselectCourse(String courseId);

    /**
     * 检验某门课程是否由某位老师教授
     *
     * @param workId 教师的工号
     * @param courseId 课程的课号
     * @return 是否匹配到结果
     */
    boolean hasCourseOfTeacher(String workId, String courseId);

    /**
     * 添加必修课程
     */
    void addRequiredCourse();

    /**
     * 获取必修课课程的课号
     *
     * @return 所有必修课课程的课号
     */
    List<String> getRequiredCourseIds();
}
