package com.hospitalRegistration.controller;
import com.hospitalRegistration.common.Result;
import com.hospitalRegistration.entity.*;
import com.hospitalRegistration.mapper.PatientMapper;
import com.hospitalRegistration.service.DoctorService;
import com.hospitalRegistration.service.RegistrationService;
import com.hospitalRegistration.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientMapper patientMapper;
    @Resource
    private RegistrationService registrationService;
    @Resource
    private RoleService roleService;
    @Resource
    private DoctorService doctorService;

    // 提交注册请求：用 Patient 接收所有注册参数
    @PostMapping("/register")
    public Map<String,Object> doRegister(@RequestBody PatientRegister patientRegister) {
        // 校验注册参数
        if (patientRegister.getUsername() == null || patientRegister.getUsername().trim().isEmpty()) {
            Map<String,Object> map = new HashMap<>();
            map.put("code",500);
            return map;
        }
        if (patientRegister.getPassword() == null || patientRegister.getPassword().trim().isEmpty()) {
            Map<String,Object> map = new HashMap<>();
            map.put("code",500);
            return map;
        }
        if (patientRegister.getRole() == null) { // 假设 role 是角色ID，需非空
            Map<String,Object> map = new HashMap<>();
            map.put("code",500);
            return map;
        }

        // 创建患者对象（newPatient 定义在外部，确保后续可访问）
        Patient newPatient = new Patient();
        newPatient.setName(patientRegister.getUsername());
        newPatient.setPassword(patientRegister.getPassword());
        newPatient.setRoleId(patientRegister.getRole());

        // 插入数据库
        int insertRows = patientMapper.insert(newPatient);
        if (insertRows <= 0) {
            Map<String,Object> map = new HashMap<>();
            map.put("code",500);
            return map;
        }
        // 注册成功
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        return map;
    }


    // 患者查询所有历史订单信息
    @GetMapping("/record")
    public Result selectRegistration(Integer userId) {
        // 根据患者id查询挂号记录
        List<Registration> registrations = registrationService.selectAllUseByPatient(userId);
        List<Map<String,Object>> listmap = new ArrayList<>();
        for (Registration registration : registrations) {
            Map<String,Object> map = new HashMap<>();
            map.put("id",registration.getNumber());
            map.put("doctorName",registration.getDoctorName());
            map.put("time",registration.getTime());
            map.put("type",registration.getType());
            map.put("status",registration.getStatusId());
            listmap.add(map);
        }
        return Result.successWithUserId(listmap);
    }

    // 患者查询所有医生信息
    @GetMapping("/doctor-list")
    public Result selectAllDoctor(Integer usereId) {
        List<Doctor> list = doctorService.selectAllDoctor();
        List<Map<String,Object>> mapList = new ArrayList<>();
        for (Doctor doctor : list) {
            Map<String,Object> map = new HashMap<>();
            map.put("doctorId",doctor.getId());
            map.put("name",doctor.getName());
            map.put("gender",doctor.getGender());
            map.put("office",doctor.getOffice());
            map.put("position",doctor.getPosition());
            map.put("study",doctor.getStudy());
            mapList.add(map);
        }
        return Result.successWithUserId(mapList);
    }

    // 患者页面挂号
    @PostMapping("/post")
    public Map<String,Object> doPostRegistration(@RequestBody PatientPost patientPost) {
        int doctorId = patientPost.getDoctorId();
        List<Registration> registrations = registrationService.selectById(doctorId);
        Registration registration = registrations.get(0);
        if (registration == null) {
            Map<String,Object> map = new HashMap<>();
            map.put("code",500);
            return map;
        }
        int registrationId = registration.getNumber();
        int patientId = patientPost.getUserId();
        Registration  newRegistration = new Registration();
        newRegistration.setPatientId(patientId);
        newRegistration.setDoctorId(doctorId);
        newRegistration.setTime(patientPost.getTime());
        newRegistration.setType(patientPost.getType());
        int insert = registrationService.postRegistration(newRegistration);
        int insert1 = roleService.updateRole(registrationId,doctorId,patientId);
        if(insert <= 0||insert1 <= 0) {
            Map<String,Object> map = new HashMap<>();
            map.put("code",500);
            return map;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        return map;
    }

    // 患者页面查某位医生的挂号信息
    @GetMapping("/selectDoctorRegistration")
    public Result selectDoctorRegistration(Integer doctorId) {
        List<Registration> registrations = registrationService.selectById(doctorId);
        List<Map<String,Object>> mapList = new ArrayList<>();
        for(Registration registration : registrations) {
            Map<String,Object> map = new HashMap<>();
            map.put("doctorId",registration.getDoctorId());
            map.put("time",registration.getTime());
            map.put("type",registration.getType());
            map.put("fee",registration.getFee());
            map.put("id",registration.getNumber());
            mapList.add(map);
        }
        Result result = Result.successWithUserId(mapList);
        return result;
    }

}
