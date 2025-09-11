package com.hospitalRegistration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    private Long id;
    private String name;
    private Integer age;
    private String password;
    private String gender;
    private String office;
    private String study;
    private String position;
    private Integer roleId;

    // 返回一个token
    private String token;
}
