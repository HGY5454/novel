package red.mlz.app.domain.novel;


import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data

public class NovelVo {
    private BigInteger novelId;
    private String title;
    private List<String> images;
    private String author;
    private Float score;
    private List<InfoVo> info;
    private String kindsName;
    private String kindsImage;
    private Integer wordCount;
    private String synopsis;
}
