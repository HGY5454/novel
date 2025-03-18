package red.mlz.console.domain.novel;

import lombok.Data;

import java.util.List;

@Data

public class NovelListVo {
    private List<ListVo> list;
    private Integer pageSize;
    private Integer total;
}
