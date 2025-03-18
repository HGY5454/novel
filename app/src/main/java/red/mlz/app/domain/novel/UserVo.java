package red.mlz.app.domain.novel;

import lombok.Data;

import java.math.BigInteger;

@Data
public class UserVo {
    private BigInteger id;
    private BigInteger phone;
    private String password;
    private String nickName;
    private String avatar;
    private String sign;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;
}
