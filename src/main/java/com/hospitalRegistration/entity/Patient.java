package com.hospitalRegistration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    private Long id;
    private String name;
    private Integer age;
    private String password;
    private Integer roleId;

    // 返回一个token
    private String token;

    // 患者的失约次数
    private Integer times;

    // 患者登录构造
    public Patient(String name, String password, Integer roleId) {
        this.name = name;
        this.password = password;
        this.roleId = roleId;
    }

}
