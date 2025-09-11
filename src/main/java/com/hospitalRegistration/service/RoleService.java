package com.hospitalRegistration.service;

import com.hospitalRegistration.entity.PatientPost;
import com.hospitalRegistration.entity.Role;
import com.hospitalRegistration.mapper.RoleMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    // 医生提交挂号信息
    public int insertRole(int registrationId,int roleId) {
        return roleMapper.insertRoleByDoctor(registrationId,roleId);
    }

    // 患者提交挂号信息
    public int updateRole(int registrationId,int roleId,int doctorId) {
        return roleMapper.updateRoleByPatient(registrationId,roleId,doctorId);
    }

}
