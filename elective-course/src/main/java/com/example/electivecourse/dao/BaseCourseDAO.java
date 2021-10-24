package com.example.electivecourse.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * 必修课和选修课的父类。定义为抽象类，不可以直接生成对象
 * 使用JsonTypeInfo注解是为了在反序列化时能够将父类对象正确地转换成子类
 *
 * @author admin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public abstract class BaseCourseDAO implements Comparable<BaseCourseDAO> {
    /**
     * id
     */
    @Builder.Default
    private String id = UUID.randomUUID().toString();

    /**
     * 课程的名称
     */
    private String name;

    /**
     * 课程号
     */
    private String courseId;

    /**
     * 授课教师的工号，这里需要用类似外键的方法处理
     */
    private String teacherWorkId;

    /**
     * 当前课程选课人数，DAO中的属性需要用包装类，否则查询时可能引起异常
     */
    @Builder.Default
    private Integer stuNum = 0;

    /**
     * 需要子类实现自己的方法
     *
     * @return 格式化后的字符串
     */
    @Override
    public String toString() {
        return "name: %s, courseId: %s, teacher workId: %s, student number: %d, "
                .formatted(name, courseId, teacherWorkId, stuNum);
    }

    /**
     * 实现课程按照选课人数进行排序
     *
     * @param course 需要进行比较的课程
     * @return 当前课程与目标课程选课人数的差
     */
    @Override
    public int compareTo(BaseCourseDAO course) {
        return this.stuNum - course.stuNum;
    }
}
