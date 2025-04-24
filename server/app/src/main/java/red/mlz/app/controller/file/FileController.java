package red.mlz.app.controller.file;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileController {
    @RequestMapping("/upload_file")
    public String uploadFile(@RequestParam("file") MultipartFile file){
        String fileUploadPath = "D:\\chrome Download\\";
        if (file.isEmpty()){
            return "文件上传失败";
        }
        String fileName = file.getOriginalFilename();
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(fileUploadPath+fileName);
            Files.write(path,bytes);
        }catch (Exception e){
        }
        return "文件保存位置:"+fileUploadPath+fileName;
    }
}
