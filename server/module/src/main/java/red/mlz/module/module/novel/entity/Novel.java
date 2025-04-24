package red.mlz.module.module.novel.entity;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class Novel {
    private BigInteger id;
    private String title;
    private String imagesUrl;
    private String author;
    private Float score;
    private Integer wordCount;
    private String synopsis;
    private BigInteger kindsId;
    private String info;
    private Integer createTime;
    private Integer updateTime;
    private Integer isDeleted;
}
