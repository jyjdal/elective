package com.example.electiveuser.service.impl;

import com.example.electivecommon.dto.ElectiveResult;
import com.example.electiveuser.service.BaseLoginService;
import com.example.electiveuser.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author admin
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService, BaseLoginService, InitializingBean, DisposableBean {
    @Override
    public ElectiveResult login(String account, String password) {
        return null;
    }

    @Override
    public void destroy() {

    }

    @Override
    public void afterPropertiesSet() {

    }
}
