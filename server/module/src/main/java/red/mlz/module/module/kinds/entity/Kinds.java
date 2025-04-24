package red.mlz.module.module.kinds.entity;
import lombok.Data;

import java.math.BigInteger;

@Data

public class Kinds {
        private BigInteger parentId;
        private BigInteger id;
        private String kindsName;
        private String kindsImage;
        private Integer createTime;
        private Integer updateTime;
        private Integer isDeleted;
}
