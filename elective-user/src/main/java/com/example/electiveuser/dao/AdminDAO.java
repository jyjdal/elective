package com.example.electiveuser.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author admin
 */
@Data
@AllArgsConstructor
public class AdminDAO {
    private String id;
    private String account;
    private String password;
}
