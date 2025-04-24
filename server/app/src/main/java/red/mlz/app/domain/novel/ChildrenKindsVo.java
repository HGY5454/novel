package red.mlz.app.domain.novel;

import lombok.Data;

import java.util.List;

@Data
public class ChildrenKindsVo {
    private List<KindsVo> kindsVoList;
    private List<ListVo> novelListVoList;
}
