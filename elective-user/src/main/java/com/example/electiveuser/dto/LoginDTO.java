package com.example.electiveuser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author admin
 */
@Data
@AllArgsConstructor
@ToString
public class LoginDTO {
    private Boolean success;
    private String message;
}
