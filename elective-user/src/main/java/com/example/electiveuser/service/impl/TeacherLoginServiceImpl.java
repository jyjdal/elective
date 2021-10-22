package com.example.electiveuser.service.impl;

import com.example.electivecommon.config.LoginStatus;
import com.example.electivecommon.dto.ElectiveResult;
import com.example.electivecommon.enums.LoginType;
import com.example.electiveuser.service.BaseLoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author admin
 */
@Service
public class TeacherLoginServiceImpl implements BaseLoginService {
    @Resource
    private TeacherServiceImpl teacherService;

    @Resource
    private LoginStatus loginStatus;

    @Override
    public ElectiveResult login(String account, String password) {
        if (!teacherService.hasTeacherWithAccount(account)) {
            return new ElectiveResult(false, "Account doesn't exist.");
        }
        if (!teacherService.verifyTeacher(account, password)) {
            return new ElectiveResult(false, "Password wrong!");
        } else {
            // 登录成功，变更当前登录状态
            loginStatus.setLoggedIn(true);
            loginStatus.setLoginType(LoginType.TEACHER);
            loginStatus.setAccount(account);
            loginStatus.setPassword(password);
            return new ElectiveResult(true, "Successfully logged in.");
        }
    }
}
