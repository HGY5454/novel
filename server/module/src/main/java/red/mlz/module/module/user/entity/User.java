package red.mlz.module.module.user.entity;

import lombok.Data;

import java.math.BigInteger;

@Data
public class User {
    private BigInteger id;
    private String phone;
    private String password;
    private String nickName;
    private String avatar;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;
}
