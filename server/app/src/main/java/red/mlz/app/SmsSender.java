package red.mlz.app;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;

import java.math.BigInteger;

public class SmsSender {
    public static Client createClient() throws Exception {
        Config config = new Config()
                // 配置 AccessKey ID，请确保代码运行环境设置了环境变量。
                .setAccessKeyId(System.getenv("ALIBABA_SMS_ACCESS_KEY_ID"))
                // 配置 AccessKey Secret，请确保代码运行环境设置了环境变量。
                .setAccessKeySecret(System.getenv("ALIBABA_SMS_ACCESS_KEY_SECRET"));
        // System.getenv()方法表示获取系统环境变量，请配置环境变量后，在此填入环境变量名称，不要直接填入AccessKey信息。

        // 配置 Endpoint
        config.endpoint = "dysmsapi.aliyuncs.com";

        return new Client(config);
    }

    public static SendSmsResponse sendSms(BigInteger phoneNumber) throws Exception {
        // 初始化请求客户端
        Client client = SmsSender.createClient();

        // 构造请求对象，请填入请求参数值
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(phoneNumber.toString())
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909");

        // 获取响应对象
        SendSmsResponse sendSmsResponse = client.sendSms(sendSmsRequest);
        // 响应包含服务端响应的 body 和 headers
        return sendSmsResponse;
    }
}