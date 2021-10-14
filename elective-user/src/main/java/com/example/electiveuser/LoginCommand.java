package com.example.electiveuser;

import com.example.electivecommon.enums.LoginType;
import com.example.electiveuser.dto.LoginDTO;

/**
 * @author admin
 */
public class LoginCommand {
    public LoginDTO login(LoginType loginType, String account, String password) {
        return new LoginDTO(true, "Successfully logged in.");
    }
}
