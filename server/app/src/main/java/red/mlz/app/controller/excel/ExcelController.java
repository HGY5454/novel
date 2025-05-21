package red.mlz.app.controller.excel;

import com.alibaba.excel.EasyExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import red.mlz.app.ExcelUtils;
import red.mlz.app.domain.excel.KindsExcel;
import red.mlz.app.domain.excel.NovelExcel;
import red.mlz.app.domain.novel.KindsListVo;
import red.mlz.app.domain.novel.KindsVo;
import red.mlz.module.module.kinds.entity.Kinds;
import red.mlz.module.module.kinds.service.KindsService;
import red.mlz.module.module.novel.entity.Novel;
import red.mlz.module.module.novel.service.NovelService;
import red.mlz.module.utils.Response;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipOutputStream;

@RestController
public class ExcelController {
    @Autowired
    private KindsService kindsService;
    @Autowired
    private NovelService novelService;

    @PostMapping("/upload_excel")
    public Response uploadExcel(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return new Response<>(1002);
        }
        try {
            InputStream inputStream = file.getInputStream();
            List<NovelExcel> novelList = EasyExcel.read(inputStream)
                    .head(NovelExcel.class)
                    .sheet()
                    .doReadSync();
            return new Response<>(1001,novelList.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/get_excel/kinds")
    public void getKinds(HttpServletResponse response){
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment;filename=分类表.xlsx");

            List<Kinds> kindsList = kindsService.getAllKinds();
            List<KindsExcel> kindsExcelList = new ArrayList<>();
            for (Kinds kinds : kindsList) {
                KindsExcel kindsExcel = new KindsExcel();
                kindsExcel.setId(kinds.getId());
                kindsExcel.setKindsName(kinds.getKindsName());
                kindsExcel.setParentId(kinds.getParentId());
                kindsExcelList.add(kindsExcel);
            }

            EasyExcel.write(response.getOutputStream(), KindsExcel.class)
                    .sheet("Sheet1")
                    .doWrite(kindsExcelList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping("/get_allExcel")
    public void getAllExcel(HttpServletResponse response){
        response.setContentType("application/zip");
        response.setHeader("Content-Disposition", "attachment; filename=export_data.zip");

        List<Kinds> kindsList = kindsService.getAllKinds();
        List<KindsExcel> kindsExcelList = new ArrayList<>();
        for (Kinds kinds : kindsList) {
            KindsExcel kindsExcel = new KindsExcel();
            kindsExcel.setId(kinds.getId());
            kindsExcel.setKindsName(kinds.getKindsName());
            kindsExcel.setParentId(kinds.getParentId());
            kindsExcelList.add(kindsExcel);
        }
        List<Novel> novelList = novelService.getAllNovel();
        List<NovelExcel> novelExcelList = new ArrayList<>();
        for (Novel novel : novelList) {
            NovelExcel novelExcel = new NovelExcel();
            novelExcel.setTitle(novel.getTitle());
            novelExcel.setAuthor(novel.getAuthor());
            novelExcel.setWordCount(novel.getWordCount());
            novelExcelList.add(novelExcel);
        }
        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            ExcelUtils excelUtils = new ExcelUtils();
            excelUtils.addExcelToZip(zipOut, "novels.xlsx", novelExcelList);

            excelUtils.addExcelToZip(zipOut, "kinds.xlsx", kindsExcelList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
