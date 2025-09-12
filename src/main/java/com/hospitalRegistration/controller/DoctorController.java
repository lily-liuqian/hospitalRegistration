package com.hospitalRegistration.controller;
import com.hospitalRegistration.common.Result;
import com.hospitalRegistration.entity.*;
import com.hospitalRegistration.mapper.DoctorMapper;
import com.hospitalRegistration.service.DoctorService;
import com.hospitalRegistration.service.RegistrationService;
import com.hospitalRegistration.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hospitalRegistration.entity.Schedule;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorMapper doctorMapper;
    @Resource
    private DoctorService doctorService;
    @Resource
    private RegistrationService registrationService;
    @Resource
    private RoleService roleService;

    @PostMapping("/register")
    public int doRegister(@RequestBody DoctorRegister doctorRegister) {
        // 校验注册参数

        // 这里的Account和name中的数据是一致的
        if (doctorRegister.getAccount()== null|| doctorRegister.getAccount().trim().isEmpty()) {
            return Result.errorCode();
        }
        if (doctorRegister.getPassword()== null|| doctorRegister.getPassword().trim().isEmpty()) {
            return Result.errorCode();
        }
        if (doctorRegister.getGender()== null|| doctorRegister.getGender().trim().isEmpty()) {
            return Result.errorCode();
        }
        if (doctorRegister.getOffice()== null|| doctorRegister.getOffice().trim().isEmpty()) {
            return Result.errorCode();
        }
        if (doctorRegister.getPosition()== null|| doctorRegister.getPosition().trim().isEmpty()) {
            return Result.errorCode();
        }
        if (doctorRegister.getStudy()== null|| doctorRegister.getStudy().trim().isEmpty()) {
            return Result.errorCode();
        }

        // 创建医生对象
        Doctor doctor = new Doctor();
        doctor.setName(doctorRegister.getName());
        doctor.setPassword(doctorRegister.getPassword());
        doctor.setGender(doctorRegister.getGender());
        doctor.setOffice(doctorRegister.getOffice());
        doctor.setPosition(doctorRegister.getPosition());
        doctor.setStudy(doctorRegister.getStudy());
        doctor.setRoleId(1);

        // 新增医生（调用 Mapper 的 insert 方法）
        int insert = doctorMapper.insertLogin(doctor);
        if (insert <= 0) {
            return Result.errorCode();
        }
        // 注册成功，返回成功信息
        return Result.successCode();
    }

    // 医生界面的医生个人信息展示
    @GetMapping("/show")
    public Map<String,Object> showDoctor(Long userId){
        Doctor doctor = doctorService.selectDoctorById(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("name",doctor.getName());
        map.put("age",doctor.getAge());
        map.put("office",doctor.getOffice());
        map.put("position",doctor.getPosition());
        map.put("study",doctor.getStudy());
        return map;
    }

    // 修改医生信息
    @GetMapping("/info")
    public Result updateDoctor(@RequestBody Doctor doctor) {
        // 1. 基础参数校验（避免空指针和无效更新）
        if (doctor == null) {
            return Result.fail("请求参数不能为空");
        }
        // 修改必须基于主键 id，否则无法定位要更新的记录
        if (doctor.getId() == null) {
            return Result.fail("医生 ID 不能为空（需指定修改的医生）");
        }

        // 2. 调用 Service 的“修改方法”（而非插入方法），执行更新逻辑
        // 注：需确保 DoctorService 中存在 updateDoctor 方法，且内部调用 Mapper 的更新 SQL
        int affectedRows = doctorService.updateDoctor(doctor);

        // 3. 根据修改结果返回对应响应
        if (affectedRows > 0) {
            // 成功：返回“修改成功”提示 + 最新的医生信息（嵌套在 data 中，与全局响应结构一致）
            return Result.successWithUserId(doctor);
        } else if (affectedRows == 0) {
            // 无影响行数：可能是 ID 不存在，或传入的字段与数据库一致（未实际修改）
            return Result.fail("未找到该医生，或信息无变化");
        } else {
            // 影响行数 < 0：通常是 SQL 执行异常（如字段约束冲突）
            return Result.fail("修改失败，请检查数据格式");
        }
    }

    // 医生手动创建挂号信息
    @PostMapping("/regis-message/insert")
    public Result insertRegistration(@RequestBody Registration registration) {
        // 基础参数校验(避免空指针和无效更新)
        if (registration == null) {
            return Result.fail("请求参数不能为空");
        }
        // 必须要指定创建挂号信息的医生id
        if(registration.getDoctorId() == null){
            return Result.fail("医生id不能为空(需指定创建挂号信息的医生)");
        }
        // 必须要指定创建挂号信息的医生姓名
        if(registration.getDoctorName() == null){
            return Result.fail("医生姓名不能为空(需指定创还能挂号信息的医生)");
        }

        // 调用Service的"新增方法"
        int doctorId = registration.getDoctorId();
        int affectedRows = registrationService.insertRegistration(registration);
        int id = registration.getNumber();
        int affectedRows1 = roleService.insertRole(id, doctorId);

        if (affectedRows > 0&& affectedRows1 > 0) {
            return Result.successWithUserId(registration);
        }else if(affectedRows == 0||affectedRows1==0){
            return Result.fail("未找到该挂号订单,或信息无变化");
        }else{
            return Result.fail("修改失败,请检查数据格式");
        }
    }

    // 医生对患者挂号信息状态管理(医生查看患者挂号信息)
    @GetMapping("/regis-message")
    public List<Map<String,Object>> selectAllRegistration(Long userId){
        List<Map<String,Object>> listmap = new ArrayList<>();
        List<Registration> registrations = doctorService.selectAllRegistrationsByDoctorId(userId);
        for (Registration registration : registrations) {
            Map<String,Object> map = new HashMap<>();
            map.put("name",registration.getPatientName());
            map.put("patientId",registration.getPatientId());
            map.put("time",registration.getTime());
            map.put("type",registration.getType());
            map.put("currentStatus",registration.getStatus());
            listmap.add(map);
        }
        return listmap;
    }

    // 医生修改患者状态
    @PostMapping("/updateStatus")
    public int updateStatus(@RequestBody UpdateStatus updateStatus) {
        int number = updateStatus.getId();
        String newStatus = updateStatus.getNewStatus();
        int insert = registrationService.updateStatus(number, newStatus);
        if (insert > 0) {
            return Result.successCode();
        } else {
            return Result.errorCode();
        }
    }

    // 医生查清患者失约次数
    @GetMapping("/selectMissNum")
    public int selectMissNum(Long patientId){
        int num = registrationService.selectMissNumById(patientId);
        return num;
    }

    // 医生排班模版设置
    @PostMapping("/schedule")
    public int setSchedule(@RequestBody List<Schedule> schedules) {
       for (Schedule schedule : schedules) {
           Integer doctorId = schedule.getUserId();
           String doctorName = doctorService.selectDoctorNameByDoctorId(doctorId);
           Registration registration = new Registration();
           registration.setDoctorId(doctorId);
           registration.setDoctorName(doctorName);
           registration.setTime(schedule.getTime());
           registration.setType(schedule.getType());
           registration.setFee(schedule.getFee());
           registration.setStatusId(0);
           registrationService.setSchedule(registration);
       }
       return Result.successCode();
    }

    // 排班模版增加操作
    @PostMapping("/scheduleInsert")
    public int addSchedule(@RequestBody List<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            Integer doctorId = schedule.getUserId();
            String doctorName = doctorService.selectDoctorNameByDoctorId(doctorId);
            Registration registration = new Registration();
            registration.setDoctorId(doctorId);
            registration.setDoctorName(doctorName);
            registration.setTime(schedule.getTime());
            registration.setType(schedule.getType());
            registration.setFee(schedule.getFee());
            registration.setStatusId(0);
            registrationService.setSchedule(registration);
        }
        return Result.successCode();
    }

    // 排班模版删除操作(根据挂号id删除单条数据)
    @GetMapping("/scheduleDelete")
    public int deleteSchedule(Integer number) {
        int delete = registrationService.deleteSchedule(number);
        if (delete > 0) {
            return Result.successCode();
        }
        return Result.errorCode();
    }

    // 排班模版删除操作(根据挂号id删除多条/所有数据)
    @PostMapping("/schedulesDelete")
    public int deleteSchedules(@RequestBody List<Integer> number) {
        for (Integer id : number) {
            registrationService.deleteSchedule(id);
        }
        return Result.successCode();
    }

    // 排班模版查询操作
    @PostMapping("/selectAllSchedule")
    public List<Map<String,Object>> selectAllSchedule() {
        List<Registration> registrations = registrationService.selectAllSchedule();
        List<Map<String,Object>> listmap = new ArrayList<>();
        for (Registration registration : registrations) {
            Map<String,Object> map = new HashMap<>();
            map.put("number",registration.getNumber());
            map.put("doctorName",registration.getDoctorName());
            map.put("time",registration.getTime());
            map.put("type",registration.getType());
            map.put("status",registration.getStatus());
            listmap.add(map);
        }
        return listmap;
    }

    // 排班模版修改操作
    @PostMapping("/updateSchedule")
    public int updateSchedule(@RequestBody Schedule schedule) {
        Integer doctorId = schedule.getUserId();
        Integer number = schedule.getNumber();
        String doctorName = doctorService.selectDoctorNameByDoctorId(doctorId);
        Registration registration = new Registration();
        registration.setNumber(number);
        registration.setDoctorId(doctorId);
        registration.setDoctorName(doctorName);
        registration.setTime(schedule.getTime());
        registration.setType(schedule.getType());
        registration.setFee(schedule.getFee());
        int updateNum = registrationService.updateSchedule(registration);
        if (updateNum > 0) {
            return Result.successCode();
        }else{
            return Result.errorCode();
        }
    }
}
