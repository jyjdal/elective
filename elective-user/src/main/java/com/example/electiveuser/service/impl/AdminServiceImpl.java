package com.example.electiveuser.service.impl;

import com.example.electivecommon.constant.DataFileName;
import com.example.electiveuser.dao.AdminDAO;
import com.example.electiveuser.service.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author admin
 */
@Service
public class AdminServiceImpl implements AdminService, InitializingBean, DisposableBean {
    private AdminDAO currentAdmin;

    @Override
    public boolean accountExists(String account) {
        return currentAdmin.getAccount().equals(account);
    }

    @Override
    public boolean verifyAccount(String account, String password) {
        return currentAdmin.getAccount().equals(account)
                && currentAdmin.getPassword().equals(password);
    }

    @Override
    public void afterPropertiesSet() {
        currentAdmin = new AdminDAO("123", "admin", "1234");
    }

    @Override
    public void destroy() throws Exception {
        File file = new File(DataFileName.ADMIN_FILE_NAME);
        new ObjectMapper().writeValue(file, currentAdmin);
    }
}
