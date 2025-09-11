package com.hospitalRegistration.service;

import com.hospitalRegistration.entity.PatientPost;
import com.hospitalRegistration.entity.Registration;
import com.hospitalRegistration.entity.UpdateStatus;
import com.hospitalRegistration.mapper.RegistrationMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RegistrationService {
    @Resource
    private RegistrationMapper registrationMapper;

    // 患者查询所有挂号信息
    public List<Registration> selectAllUseByPatient(Integer number) {
        return registrationMapper.selectAllUseByPatient(number);
    }

    // 医生手动创建挂号信息
    public int insertRegistration(Registration registration) {
        return registrationMapper.insertRegistration(registration);
    }

    // 患者提交挂号信息(患者挂号)
    public int postRegistration(Registration registration) {
        return registrationMapper.postRegistration(registration);
    }

    // 通过医生id查询挂号信息
    public List<Registration> selectById(Integer number) {
        return registrationMapper.selectById(number);
    }

    // 医生修改患者状态
    public int updateStatus(int number, @Param("status") String status) {
        return registrationMapper.updateStatus(number, status);
    }

    // 医生查看患者失约次数
    public int selectMissNumById(Long patientId) {
        return registrationMapper.selectMissNumById(patientId);
    }

    // 医生添加排班模版
    public int setSchedule(Registration registration) {
        return registrationMapper.setSchedule(registration);
    }

    // 排版模版删除操作
    public int deleteSchedule(Integer number) {
        return registrationMapper.deleteSchedule(number);
    }

    // 查看排版模版
    public List<Registration> selectAllSchedule() {
        return registrationMapper.selectAllSchedule();
    }

    // 修改排班信息
    public int updateSchedule(Registration registration) {
        return registrationMapper.updateSchedule(registration);
    }
}

