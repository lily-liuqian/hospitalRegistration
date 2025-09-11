package com.hospitalRegistration.mapper;

import com.hospitalRegistration.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
public interface RoleMapper {
    // 医生提交挂号信息
    int insertRoleByDoctor(@Param("registrationId") int registrationId,@Param("doctorId")int doctorId);
    // 患者提交挂号信息
    int updateRoleByPatient(@Param("registrationId")  int registrationId,@Param("doctorId")int doctorId,@Param("patientId") int patientId);
}
