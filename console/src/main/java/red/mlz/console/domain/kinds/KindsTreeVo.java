package red.mlz.console.domain.kinds;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

@Data
public class KindsTreeVo {
    private BigInteger id;
    private String kindsImage;
    private String kindsName;
    private BigInteger parentId;
    private List<KindsTreeVo> children;
}
