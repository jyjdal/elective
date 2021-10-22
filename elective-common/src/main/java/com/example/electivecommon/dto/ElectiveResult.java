package com.example.electivecommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author admin
 */
@Data
@AllArgsConstructor
@ToString
public class ElectiveResult {
    private Boolean success;
    private String message;
}
