package com.hospitalRegistration.controller;


import com.hospitalRegistration.entity.LoginParam;
import com.hospitalRegistration.entity.Doctor;
import com.hospitalRegistration.entity.Patient;
import com.hospitalRegistration.mapper.DoctorMapper;
import com.hospitalRegistration.mapper.PatientMapper;
import com.hospitalRegistration.common.Result;
import com.hospitalRegistration.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*
    登录成功返回token和userId
    登录失败则返回null
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/login")
public class loginController {
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private JwtUtils jwtUtils;

    // 2. 提交登录请求：先查患者，再查医生，自动分流
    @PostMapping
    public Map<String, Object> Login(@RequestBody LoginParam loginParam) {

        try {
            Integer roleId = loginParam.getRole();
            String name = loginParam.getUsername();
            String password = loginParam.getPassword();

            // 医生登录（role_id=1）
            if (roleId == 1) {
                Doctor doctor = doctorMapper.getDoctorByName(name);
                if (doctor == null) {
                    System.out.println("医生账户不存在");
                    return null;
                }
                if (!doctor.getPassword().equals(password)) {
                    System.out.println("医生密码错误");
                    return null;
                }
                // 生成Token
                String token = jwtUtils.generateToken(doctor.getId(), roleId);
                // 构建返回结果（包含用户信息和Token）
                Map<String, Object> data = new HashMap<>();
                Long id = doctor.getId();
                data.put("code", 200);
                data.put("userId", id);
                data.put("token", token);
                System.out.println("登录成功");
                return data;
            }
            else if (roleId == 0) {
                Patient patient = patientMapper.getPatientByName(name);
                if (patient == null) {
                    System.out.println("患者账号不存在");
                    return null;
                }
                if (!patient.getPassword().equals(password)) {
                    System.out.println("患者密码错误");
                    return null;
                }
                // 生成Token
                String token = jwtUtils.generateToken(patient.getId(), roleId);
                // 构建返回结果
                Map<String, Object> data = new HashMap<>();
                Long id = patient.getId();
                data.put("code", 200);
                data.put("userId", id);
                data.put("token", token);
                System.out.println("登录成功");
                return data;
            } else {
                System.out.println("无效role_id：仅支持1（医生）或0（患者）");
                return null;
            }

        } catch (Exception e) {
            // 捕获异常，返回服务器错误
            System.out.println("登录失败：" + e.getMessage());
            return null;
        }

        }
    }
