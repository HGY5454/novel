package red.mlz.app.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Data
@Accessors(chain = false)
public class KindsExcel {
    @ExcelProperty(value = "ID")
    private BigInteger id;
    @ExcelProperty(value = "分类名称")
    private String kindsName;
    @ExcelProperty(value = "父类ID")
    private BigInteger parentId;
}
