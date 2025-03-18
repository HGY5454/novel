package red.mlz.app.domain.novel;

import lombok.Data;
import red.mlz.module.module.novel.entity.Info;

import java.util.List;

@Data
public class ListVo {
    private String title;
    private Image image;
    private String author;
    private Float score;
    private String kindsName;
    private List<InfoVo> info;
}
