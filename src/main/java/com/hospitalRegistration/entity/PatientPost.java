package com.hospitalRegistration.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientPost {
    int doctorId;
    String doctorName;
    // 患者id
    int userId;
    // 患者姓名
    String name;
    String time;
    String type;
}
