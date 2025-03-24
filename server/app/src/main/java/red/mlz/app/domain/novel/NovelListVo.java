package red.mlz.app.domain.novel;


import lombok.Data;

import java.util.List;

@Data

public class NovelListVo{
    private List<ListVo> list;
    private Boolean isEnd;
    private String wp;
}
