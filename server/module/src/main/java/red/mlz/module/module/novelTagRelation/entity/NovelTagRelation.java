package red.mlz.module.module.novelTagRelation.entity;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class NovelTagRelation {
    private BigInteger id;
    private BigInteger novelId;
    private BigInteger tagId;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDelete;
}
