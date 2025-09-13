package com.hospitalRegistration.mapper;

import com.hospitalRegistration.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RegistrationMapper {
    // 患者查看挂号信息
    List<Registration> selectAllUseByPatient(Integer patientId);

    // 医生创建挂号订单
    int insertRegistration(Registration registration);

    // 根据医生id查询挂号订单/医生查看挂号信息
    List<Registration> selectById(Integer id);

    // 患者提交挂号信息
    int postRegistration(Registration registration);

    // 医生修改患者状态
    int updateStatus(@Param("number")  int number, @Param("status") String status);

    // 医生查看患者失约次数
    int selectMissNumById(Long patientId);

    // 医生排班模版设置
    int setSchedule(Registration registration);

    // 排版模版删除操作
    int deleteSchedule(Integer number);

    // 查看排版模版
    List<Registration> selectAllSchedule();

    // 排班模版修改操作
    int updateSchedule(Registration registration);

    // 根据医生id查询挂号id
    Integer selectNumberByDoctorId(Integer doctorId);
}
