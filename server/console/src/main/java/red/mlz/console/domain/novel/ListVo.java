package red.mlz.console.domain.novel;

import lombok.Data;

@Data
public class ListVo {
    private String title;
    private String image;
    private String author;
    private Float score;

    private String createTime;
    private String updateTime;
}
