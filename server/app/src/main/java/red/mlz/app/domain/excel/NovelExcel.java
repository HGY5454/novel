package red.mlz.app.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = false)
public class NovelExcel {

     @ExcelProperty(value = "标题")
     private String title;
     @ExcelProperty(value = "作者")
     private String author;
     @ExcelProperty(value = "字数")
     private Integer wordCount;

}
