package com.hospitalRegistration.service;

import com.hospitalRegistration.entity.Doctor;
import com.hospitalRegistration.entity.Registration;
import com.hospitalRegistration.mapper.DoctorMapper;
import com.hospitalRegistration.mapper.RegistrationMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DoctorService {
    @Resource
    private DoctorMapper doctorMapper;

    // 查询所有医生信息
    public List<Doctor> selectAllDoctor() {
        return doctorMapper.selectList();
    }

    // 修改医生信息(其实应该让管理员做...)
    public int updateDoctor(Doctor doctor) {
        return doctorMapper.updateById(doctor);
    }

    // 根据医生id查询挂号订单
    public Doctor selectDoctorById(Long id) {
        return doctorMapper.selectById(id);
    }

    // 医生对患者信息进行管理
    //selectPatientRegistrationsByDoctorId
    public List<Registration> selectAllRegistrationsByDoctorId(Long doctorId) {
        return doctorMapper.selectPatientRegistrationsByDoctorId(doctorId);
    }

    // 根据医生id查询医生姓名
    public String selectDoctorNameByDoctorId(int doctorId) {
        return doctorMapper.getDoctorNameByDoctorId(doctorId);
    }
}
