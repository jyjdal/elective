package com.example.electiveuser.service.impl;

import com.example.electivecommon.config.LoginStatus;
import com.example.electivecommon.enums.LoginType;
import com.example.electiveuser.dto.LoginDTO;
import com.example.electiveuser.service.AdminService;
import com.example.electiveuser.service.BaseLoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author admin
 */
@Service
public class AdminLoginServiceImpl implements BaseLoginService {
    @Resource
    private AdminService adminService;

    @Resource
    private LoginStatus loginStatus;

    @Override
    public LoginDTO login(String account, String password) {
        if (!adminService.accountExists(account)) {
            return new LoginDTO(false, "Account doesn't exist.");
        }
        if (!adminService.verifyAccount(account, password)) {
            return new LoginDTO(false, "Password wrong!");
        } else {
            loginStatus.setLoggedIn(true);
            loginStatus.setLoginType(LoginType.ADMIN);
            loginStatus.setAccount("admin");
            return new LoginDTO(true, "Successfully logged in.");
        }
    }
}
