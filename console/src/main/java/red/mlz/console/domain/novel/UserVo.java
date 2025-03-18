package red.mlz.console.domain.novel;

import lombok.Data;

import java.math.BigInteger;

@Data
public class UserVo {
    private BigInteger id;
    private String nickName;
    private String avatar;
    private String sign;
}
