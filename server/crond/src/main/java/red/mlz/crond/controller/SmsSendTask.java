package red.mlz.crond.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.web.bind.annotation.RestController;
import red.mlz.module.module.sms.entity.SmsTask;
import red.mlz.module.module.sms.service.SmsRecordService;
import red.mlz.module.module.sms.service.SmsTaskService;

import java.util.List;


@RestController
public class SmsSendTask {
    @Autowired
    private SmsTaskService smsTaskService;
    @Autowired
    private SmsRecordService smsRecordService;
    @Scheduled(fixedRate = 60000) // 每分钟执行
    public void processTasks() throws Exception {
        List<SmsTask> smsTask = smsTaskService.getListSmsTask();
        for (SmsTask task : smsTask) {
            if (task.getStatus() == 0) {
                if (SmsSender.sendSms(task.getPhone()).getBody().getCode() == "ok" ){
                    smsTaskService.updateSmsTask(task.getId(),task.getPhone(),1);
                    smsRecordService.insertSmsRecord(task.getPhone(),1);
                }else {
                    smsRecordService.insertSmsRecord(task.getPhone(),2);
                }

            }
        }
    }
}
