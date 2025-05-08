package red.mlz.app.controller.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.app.controller.SmsSender;
import red.mlz.module.module.sms.service.SmsRecordService;
import red.mlz.module.module.sms.service.SmsTaskService;

import java.math.BigInteger;


@RestController
public class SmsController {
    @Autowired
    private SmsTaskService smsTaskService;
    @Autowired
    private SmsRecordService smsRecordService;

    @RequestMapping("/send")
    public String send(@RequestParam(name = "phone") BigInteger phone) throws Exception {
        String result = SmsSender.sendSms(phone).getBody().getCode();
        smsRecordService.insertSmsRecord(phone,result=="ok"?1:2);
        return result=="ok"?"成功":"失败";
    }

    @Async("smsExecutor")
    public String sendAsync(@RequestParam(name = "phone") BigInteger phone) throws Exception{
        String result = SmsSender.sendSms(phone).getBody().getCode();
        smsRecordService.insertSmsRecord(phone,result=="ok"?1:2);
        return result=="ok"?"成功":"失败";
    }

    @RequestMapping("/creat_task")
    public String creatTask(@RequestParam(name = "phone") BigInteger phone) throws Exception {
        int result = smsTaskService.insertSmsTask(phone);
        return result == 1 ?"成功":"失败";
    }
}
