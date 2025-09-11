package com.hospitalRegistration.mapper;

import com.hospitalRegistration.entity.Doctor;
import com.hospitalRegistration.entity.Registration;
import org.apache.ibatis.annotations.Mapper;

import javax.print.Doc;
import java.util.List;

@Mapper
public interface DoctorMapper {
    // 医生信息添加
    int insertDoctor(Doctor doctor);

    // 校验医生id唯一性（避免重复注册）
    int countDoctorById(Long id);

    // 登录查询:根据医生姓名查询医生信息
    Doctor getDoctorByName(String name);

    // 新增医生（注册用）
    int insertLogin(Doctor doctor);
    //根据 id 删除医生
    int daleteById(Long id);
    //根据 id 修改医生信息
    int updateById(Doctor doctor);
    //根据 id 查询医生（首页展示用）
    Doctor selectById(Long id);
    //查询所有医生
    List<Doctor> selectList();
    // 医生对患者信息进行管理
    List<Registration> selectPatientRegistrationsByDoctorId(Long doctorId);

    // 根据医生id查询医生姓名
    String getDoctorNameByDoctorId(int doctorId);
}
