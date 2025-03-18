package red.mlz.module.module.tags.entity;

import lombok.Data;

import java.math.BigInteger;

@Data
public class Tag {
    private Integer id;
    private String tagName;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDelete;
}
