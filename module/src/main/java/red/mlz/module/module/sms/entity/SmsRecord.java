package red.mlz.module.module.sms.entity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = true)
public class SmsRecord{
        private BigInteger id;
        private BigInteger phone;
        private Integer status;
        private Integer createTime;
        private Integer updateTime;
        private Integer isDeleted;
}
