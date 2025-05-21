package red.mlz.app;

import com.alibaba.excel.EasyExcel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExcelUtils {
    public void addExcelToZip(ZipOutputStream zipOut, String filename, List<?> data) throws IOException {

        ByteArrayOutputStream excelOut = new ByteArrayOutputStream();
        EasyExcel.write(excelOut, data.get(0).getClass()).sheet("Sheet1").doWrite(data);

        zipOut.putNextEntry(new ZipEntry(filename));
        zipOut.write(excelOut.toByteArray());
        zipOut.closeEntry();
    }

}
