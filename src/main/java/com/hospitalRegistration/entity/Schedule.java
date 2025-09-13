package com.hospitalRegistration.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {
    private Integer doctorId;
    private List<Map<String,Object>> schedules;
}
