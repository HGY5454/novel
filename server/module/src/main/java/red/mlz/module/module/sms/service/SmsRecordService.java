package red.mlz.module.module.sms.service;

import red.mlz.module.module.sms.entity.SmsRecord;
import red.mlz.module.module.sms.mapper.SmsRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class SmsRecordService {
    @Autowired
    private SmsRecordMapper smsRecordMapper;

    public List<SmsRecord> getListSmsRecord()
    {
        return smsRecordMapper.getListSmsRecord();
    }


    public SmsRecord getSmsRecordInfoById( Integer id) {
        return smsRecordMapper.getById(id);
    }

    public SmsRecord getSmsRecordById( Integer id) {
    return smsRecordMapper.extractById(id);
    }

    public int insertSmsRecord(BigInteger phone , Integer status) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setPhone(phone);
        smsRecord.setStatus(status);
        smsRecord.setCreateTime(timestamp);
        smsRecord.setUpdateTime(timestamp);
        smsRecord.setIsDeleted(0);

        return smsRecordMapper.insert(smsRecord);
    }

    public int insertSmsRecordWithOptimisticLock(BigInteger phone, Integer status) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setPhone(phone);
        smsRecord.setStatus(status);
        smsRecord.setCreateTime(timestamp);
        smsRecord.setUpdateTime(timestamp);
        smsRecord.setIsDeleted(0);
        smsRecord.setVersion(0); // 初始版本号为0

        int affectedRows = smsRecordMapper.insertWithVersion(smsRecord);
        if (affectedRows == 0) {
            throw new RuntimeException("Optimistic lock conflict - insert failed");
        }

        // 使用乐观锁更新记录
        smsRecord.setStatus(status + 1); // 示例：更新状态
        affectedRows = smsRecordMapper.updateWithOptimisticLock(smsRecord);
        if (affectedRows == 0) {
            throw new RuntimeException("Optimistic lock conflict - update failed");
        }
        return affectedRows;
    }

    public int updateSmsRecord(BigInteger id , BigInteger phone, Integer status) {
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        SmsRecord smsRecord = new SmsRecord();
        smsRecord.setId(id);
        smsRecord.setPhone(phone);
        smsRecord.setStatus(status);
        smsRecord.setCreateTime(timestamp);
        smsRecord.setUpdateTime(timestamp);
        smsRecord.setIsDeleted(0);

        return smsRecordMapper.update(smsRecord);
    }


    public int deleteSmsRecord( Integer id) {
    return smsRecordMapper.delete((int) (System.currentTimeMillis() / 1000),id);
    }
}
