package red.mlz.module.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunSmsConfig {
    private String accessKeyId;
    private String accessKeySecret;
    private String regionId;
    private String signName;
    private String templateCode;
}
