package red.mlz.module.module.novelTagRelation.entity;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class NovelTagRelation {
    private String id;
    private BigInteger novelId;
    private Integer tagsId;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDelete;
}
