package com.hospitalRegistration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    private Integer number;
    private String doctorName;
    private String time;
    // 挂号类别
    private String type;
    private Integer fee;
    private Integer statusId;
    private Integer doctorId;
    private Integer patientId;
    // 额外增加的元素:status,patientName
    private String status;
    private String patientName;
}
