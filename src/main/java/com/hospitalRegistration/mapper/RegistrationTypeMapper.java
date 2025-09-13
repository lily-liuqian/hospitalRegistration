package com.hospitalRegistration.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegistrationTypeMapper {
    //-- 根据挂号类型的id来查询挂号类型
    String selectTypeById(Integer id);
}
