package red.mlz.app.domain.novel;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class KindsListVo {
    private List<KindsVo> kindsList;
    private String kindsName;
    private String kindsImages;
    private BigInteger id;
}
