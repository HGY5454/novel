package red.mlz.app.controller.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.app.SmsSender;
import red.mlz.module.module.sms.service.SmsRecordService;
import red.mlz.module.module.sms.service.SmsTaskService;
import red.mlz.module.utils.Response;

import java.math.BigInteger;


@RestController
public class SmsController {
    @Autowired
    private SmsTaskService smsTaskService;
    @Autowired
    private SmsRecordService smsRecordService;

    @RequestMapping("/send")
    public Response send(@RequestParam(name = "phone") BigInteger phone) {
        int result = 1002;
        try {
            if (SmsSender.sendSms(phone).getBody().getCode()=="ok"){
                result = 1001;
                smsRecordService.insertSmsRecord(phone, 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Response(result);
    }


    @RequestMapping("/creat_task")
    public Response creatTask(@RequestParam(name = "phone") BigInteger phone){
        if (smsTaskService.insertSmsTask(phone)==1){
            return new Response(1001);
        }else {
            return new Response(1002);
        }
    }
}
