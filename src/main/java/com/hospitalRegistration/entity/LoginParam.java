package com.hospitalRegistration.entity;

import lombok.Data;

@Data
public class LoginParam {
    private String username;
    private String password;
    private Integer role;
}
