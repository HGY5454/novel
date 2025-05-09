package red.mlz.app.crond;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import red.mlz.app.SmsSender;
import red.mlz.module.module.sms.entity.SmsTask;
import red.mlz.module.module.sms.service.SmsRecordService;
import red.mlz.module.module.sms.service.SmsTaskService;

import java.util.List;

@Slf4j
@Component
public class SmsSendCrond {
    @Autowired
    private SmsTaskService smsTaskService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Scheduled(fixedRate = 60000) // 每分钟执行
    public void processTasks() throws Exception {
        List<SmsTask> unSendSmsTask = smsTaskService.getUnSendSmsTask();
        for (SmsTask task : unSendSmsTask) {
            if (SmsSender.sendSms(task.getPhone()).getBody().getCode() == "ok" ){
                smsTaskService.updateSmsTask(task.getId(),task.getPhone(),1);
                smsRecordService.insertSmsRecord(task.getPhone(),1);
            }else {
                smsRecordService.insertSmsRecord(task.getPhone(),2);
            }
        }
    }
}
