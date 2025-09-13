package com.hospitalRegistration.service;

import com.hospitalRegistration.mapper.RegistrationTypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RegistrationTypeService {
    @Resource
    private RegistrationTypeMapper registrationTypeMapper;

    // 根据挂号类型id来查询挂号类型
    public String selectTypeById(Integer id) {
        return registrationTypeMapper.selectTypeById(id);
    }
}
