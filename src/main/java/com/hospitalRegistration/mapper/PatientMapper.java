package com.hospitalRegistration.mapper;

import com.hospitalRegistration.entity.Patient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PatientMapper {
    // 自定义查询：根据姓名（账号）查患者信息（登录用）
    Patient getPatientByName(String name);

    //- insert (Patient patient)：新增患者（注册用）
    int insert(Patient patient);
//- deleteById (Integer id)：根据 id 删除患者
    int deleteById(Patient patient);
//- updateById (Patient patient)：根据 id 修改患者信息
    int updateById(Patient patient);
//- selectById (Long id)：根据 id 查询患者（首页展示用）
    Patient selectById(Long id);
//- selectList (null)：查询所有患者
    List<Patient> selectList();
    // 自定义查询：校验患者id是否已注册（患者注册用）
    Integer countPatientById(Long id);
}
