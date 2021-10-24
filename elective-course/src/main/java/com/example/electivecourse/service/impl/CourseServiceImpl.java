package com.example.electivecourse.service.impl;

import com.example.electivecommon.constant.DataFileName;
import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecourse.dao.BaseCourseDAO;
import com.example.electivecourse.service.CourseService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author admin
 */
@Slf4j
@Service
public class CourseServiceImpl implements CourseService, InitializingBean, DisposableBean {
    private Vector<BaseCourseDAO> courses;

    @Override
    public boolean hasCourse(String courseId) {
        return this.courses.stream().anyMatch(course -> course.getCourseId().equals(courseId));
    }

    @Override
    public ElectiveResult addCourse(BaseCourseDAO course) {
        // 如果课程号重复，那么不添加
        if (this.hasCourse(course.getCourseId())) {
            return new ElectiveResult(false, "Course id already exists!");
        }

        // 添加课程
        this.courses.add(course);
        // TODO 如果是必修课程，所有的学生都应该选修这门必修课
        return new ElectiveResult(true, "Successfully added course id: %s, name: %s, teacher id: %s."
                .formatted(course.getCourseId(), course.getName(), course.getTeacherWorkId()));
    }

    @Override
    public ElectiveResult removeCourseByCourseId(String courseId) {
        // 如果没有找到课程号，那么该删除哪门课程
        if (!this.hasCourse(courseId)) {
            return new ElectiveResult(false, "Course id doesn't exist!");
        }

        // 删除课程
        this.courses.removeIf(course -> course.getCourseId().equals(courseId));
        // TODO 删除课程的时候所有学生都应该退选这门课程
        return new ElectiveResult(true, "Successfully removed course id: %s."
                .formatted(courseId));
    }

    @Override
    public List<BaseCourseDAO> getSortedByStuNum() {
        return this.courses.stream().sorted().toList();
    }

    @Override
    public ElectiveResult setTeacherWordId(String courseId, String workId) {
        // 如果参数不存在的话
        if (!hasCourse(courseId)) {
            return new ElectiveResult(false, "Course id doesn't exist!");
        }

        // 因为课程号唯一，因此在filter之后使用allMatch
        if (this.courses.stream()
                .filter(course -> course.getCourseId().equals(courseId))
                .allMatch(course -> course.getTeacherWorkId().equals(workId))) {
            return new ElectiveResult(false, "Teacher workId doesn't change!");
        }

        // 如果找到对应的课程和教师，那么设置新的教师工号
        Iterator<BaseCourseDAO> iterator = this.courses.iterator();
        while (iterator.hasNext()) {
            BaseCourseDAO teacher = iterator.next();
            if (teacher.getCourseId().equals(courseId)) {
                // 要先获取下标，否则找不到
                int index = this.courses.indexOf(teacher);
                teacher.setTeacherWorkId(workId);
                this.courses.setElementAt(teacher, index);
                // 记得跳出循环，避免不必要的比较
                break;
            }
        }
        return new ElectiveResult(true, "Successfully set course: %s with teacher work id: %s."
                .formatted(courseId, workId));
    }

    @Override
    public List<BaseCourseDAO> getAllByTeacherWorkId(String workId) {
        return this.courses.stream()
                .filter(course -> course.getTeacherWorkId().equals(workId))
                .toList();
    }

    @Override
    public List<BaseCourseDAO> getAllByCourseIds(List<String> courseIds) {
        return this.courses.stream()
                .filter(course -> courseIds.stream()
                        .anyMatch(id -> course.getCourseId().equals(id)))
                .toList();
    }

    @Override
    public List<BaseCourseDAO> getAll() {
        return this.courses.stream().toList();
    }

    @Override
    public void afterPropertiesSet() throws IOException {
        // 初始化一个vector，防止NPE
        this.courses = new Vector<>(10);

        File dataFile = new File(DataFileName.COURSE_FILE_NAME);
        if (!dataFile.exists()) {
            // 如果数据文件没找到，代表目前没有教师信息，直接创建空列表即可
            log.info("Data file %s not found, creating an empty list."
                    .formatted(DataFileName.COURSE_FILE_NAME));
        } else {
            // 如果找到数据文件，那么就加载进来即可
            log.info("Data file %s found, start loading course data."
                    .formatted(DataFileName.COURSE_FILE_NAME));
            ObjectMapper mapper = new ObjectMapper();
            // 需要开启类型标注，否则无法生成子类对象序列化后的字符串
            mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                    ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
            this.courses = mapper.readValue(dataFile, new TypeReference<>() {
            });
        }
    }

    @Override
    public void destroy() throws IOException {
        // 将当前课程信息导出
        log.info("Dumping course data to file: %s".formatted(DataFileName.COURSE_FILE_NAME));
        File file = new File(DataFileName.COURSE_FILE_NAME);
        ObjectMapper mapper = new ObjectMapper();
        // 需要开启类型标注，否则无法生成子类对象
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        mapper.writeValue(file, this.courses);
    }
}
