package com.example.electiveuser.controller;

import com.example.electivecommon.enums.LoginType;
import com.example.electiveuser.dto.LoginDTO;
import com.example.electiveuser.service.impl.AdminLoginServiceImpl;
import com.example.electiveuser.service.impl.StudentLoginServiceImpl;
import com.example.electiveuser.service.impl.TeacherLoginServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author admin
 */
@Component
public class UserLoginController {
    @Resource
    private AdminLoginServiceImpl adminLoginService;

    @Resource
    private TeacherLoginServiceImpl teacherLoginService;

    @Resource
    private StudentLoginServiceImpl studentLoginService;


    public LoginDTO login(LoginType loginType, String account, String password) {
        return switch (loginType) {
            case ADMIN -> adminLoginService.login(account, password);
            case TEACHER -> teacherLoginService.login(account, password);
            case STUDENT -> studentLoginService.login(account, password);
        };
    }
}
