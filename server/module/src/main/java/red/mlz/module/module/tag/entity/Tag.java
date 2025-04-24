package red.mlz.module.module.tag.entity;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Tag {
    private BigInteger id;
    private String tagName;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;
}
