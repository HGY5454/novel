package red.mlz.module.module.sms.service;

import red.mlz.module.module.sms.entity.SmsTask;
import red.mlz.module.module.sms.mapper.SmsTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class SmsTaskService {
    @Autowired
    private SmsTaskMapper smsTaskMapper;

    public List<SmsTask> getListSmsTask()
    {
        return smsTaskMapper.getListSmsTask();
    }

    public SmsTask getSmsTaskInfoById( Integer id) {
        return smsTaskMapper.getById(id);
    }

    public SmsTask getSmsTaskById( Integer id) {
    return smsTaskMapper.extractById(id);
    }

    public int insertSmsTask(BigInteger phone) {
    int timestamp = (int) (System.currentTimeMillis() / 1000);
    SmsTask smsTask = new SmsTask();
    smsTask.setPhone(phone);
    smsTask.setStatus(0);
    smsTask.setCreateTime(timestamp);
    smsTask.setUpdateTime(timestamp);
    smsTask.setIsDeleted(0);

    return smsTaskMapper.insert(smsTask);
    }

    public int updateSmsTask(Integer id , BigInteger phone, Integer status) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        SmsTask smsTask = new SmsTask();
        smsTask.setId(id);
        smsTask.setPhone(phone);
        smsTask.setStatus(status);
        smsTask.setCreateTime(timestamp);
        smsTask.setUpdateTime(timestamp);
        smsTask.setIsDeleted(0);

        return smsTaskMapper.update(smsTask);
    }


    public int deleteSmsTask( Integer id) {
    return smsTaskMapper.delete((int) (System.currentTimeMillis() / 1000),id);
    }
}
