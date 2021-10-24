package com.example.electiveuser.controller;

import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecommon.enums.LoginType;
import com.example.electiveuser.service.impl.AdminServiceImpl;
import com.example.electiveuser.service.impl.StudentServiceImpl;
import com.example.electiveuser.service.impl.TeacherServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用户登录的controller类，用于分发登录的请求
 *
 * @author admin
 */
@Component
public class UserLoginController {
    @Resource
    private AdminServiceImpl adminService;

    @Resource
    private TeacherServiceImpl teacherService;

    @Resource
    private StudentServiceImpl studentService;

    public ElectiveResult login(LoginType loginType, String account, String password) {
        return switch (loginType) {
            case ADMIN -> adminService.login(account, password);
            case TEACHER -> teacherService.login(account, password);
            case STUDENT -> studentService.login(account, password);
        };
    }
}
