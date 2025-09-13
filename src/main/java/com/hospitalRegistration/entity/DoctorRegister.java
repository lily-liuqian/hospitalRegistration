package com.hospitalRegistration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRegister {
    String account;
    String password;
    String name;
    String gender;
    String office;
    String position;
    String study;
    int age;
}
