package red.mlz.console.controller.kinds;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectRequest;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import red.mlz.console.domain.kinds.KindsTreeVo;
import red.mlz.module.module.kinds.entity.Kinds;
import red.mlz.module.module.kinds.service.KindsService;
import red.mlz.module.module.novel.entity.Novel;
import red.mlz.module.module.novel.service.NovelService;
import red.mlz.module.utils.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Log4j2
public class KindsController {
    @Autowired
    private KindsService kindsService;
    @Autowired
    private NovelService novelService;

    @RequestMapping("/kinds/kinds_info")
    public Response getKinds(@RequestParam(name = "kindsId") BigInteger kindsId) {
        return new Response<>(1001, kindsService.getKindsById(kindsId));
    }

    @RequestMapping("/kinds/createKinds")
    public Response createKinds(@RequestParam(name = "kindsName") String kindsName,
                                @RequestParam(name = "kindsImages") String kindsImages) {
        int result = kindsService.insertKinds(kindsName, kindsImages);
        return result == 1 ? new Response<>(1001) : new Response<>(1002);
    }

    @RequestMapping("/kinds/updateKinds")
    public Response updateKinds(@RequestParam(name = "kindsId") BigInteger kindsId,
                                @RequestParam(name = "kindsName") String kindsName,
                                @RequestParam(name = "kindsImages") String kindsImages) {
        int result = kindsService.updateKinds(kindsId, kindsName, kindsImages);
        return result == 1 ? new Response<>(1001) : new Response<>(1002);
    }

    @RequestMapping("/kinds/deleteKinds")
    public Response deleteKinds(@RequestParam(name = "id") BigInteger id) {
        int result = kindsService.deleteKinds(id);
        List<Novel> novelList = novelService.getNovelByKindsId(id);
        for (Novel novel : novelList) {
            BigInteger kindsId = novel.getKindsId();
            novelService.delete(kindsId);
        }
        return result == 1 ? new Response<>(1001) : new Response<>(1002);
    }

    @RequestMapping("/kinds/kinds_tree")
    public Response kindsTree() {

        List<Kinds> allKinds = kindsService.getAllKinds();

        List<KindsTreeVo> allKindsTreeVo = new ArrayList<>();

        for (Kinds kinds : allKinds) {
            KindsTreeVo kindsTreeVo = new KindsTreeVo();
            kindsTreeVo.setId(kinds.getId());
            kindsTreeVo.setKindsName(kinds.getKindsName());
            kindsTreeVo.setKindsImage(kinds.getKindsImage());
            kindsTreeVo.setParentId(kinds.getParentId());
            allKindsTreeVo.add(kindsTreeVo);
        }
        Map<BigInteger, KindsTreeVo> map = new HashMap<>();
        for (KindsTreeVo kindsTreeVo : allKindsTreeVo) {
            map.put(kindsTreeVo.getId(), kindsTreeVo);
        }

        List<KindsTreeVo> root = new ArrayList<>();

        for (KindsTreeVo kindsTreeVo : allKindsTreeVo) {
            BigInteger parentId = kindsTreeVo.getParentId();
            if (parentId == null) {
                root.add(kindsTreeVo);
            } else {
                KindsTreeVo parent = map.get(parentId);
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(kindsTreeVo);
            }
        }

        return new Response<>(1001, root);
    }
}

