package red.mlz.console.controller.novel;


import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.console.annotations.VerifiedUser;
import red.mlz.console.domain.novel.*;
import red.mlz.module.module.kinds.service.KindsService;
import red.mlz.module.module.novel.entity.Novel;
import red.mlz.module.module.novel.service.NovelService;
import red.mlz.module.module.novelTagRelation.service.NovelTagRelationService;
import red.mlz.module.module.tag.service.TagService;
import red.mlz.module.module.user.entity.User;
import red.mlz.module.utils.BaseUtils;
import red.mlz.module.utils.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Log4j2
public class NovelController {
    @Autowired
    private NovelService novelService;
    @Autowired
    private KindsService kindsService;

    @RequestMapping("novel/novelList")
    public Response novelList(@VerifiedUser User loginUser,
                              @RequestParam(name = "page") Integer page,
                              @RequestParam(name = "titleKeyWord", required = false) String titleKeyWord
                              ) {

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1003);
        }
        Integer pageSize = 4;
        List<String> kindsIdList = novelService.getKindsIdByKeyword(titleKeyWord);
        String ids = String.join(",", kindsIdList);
        Integer total = novelService.getNovelCount(titleKeyWord, ids);
        List<Novel> novelList = novelService.getNovelListByPage(page, pageSize, titleKeyWord, ids);
        NovelListVo novelListVo = new NovelListVo();
        novelListVo.setList(new ArrayList<>());

        for (Novel novel : novelList) {
            ListVo listVo = new ListVo();
            List<String> Images = new ArrayList<>(Arrays.asList(novel.getImagesUrl().split("\\$")));
            listVo.setTitle(novel.getTitle());
            listVo.setImage(Images.get(0));
            listVo.setAuthor(novel.getAuthor());
            listVo.setScore(novel.getScore());
            Long createTime = Long.valueOf(novel.getCreateTime());
            Long updateTime = Long.valueOf(novel.getUpdateTime());
            createTime = createTime * 1000;
            updateTime = updateTime * 1000;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            listVo.setCreateTime(sdf.format(createTime));
            listVo.setUpdateTime(sdf.format(updateTime));
            novelListVo.getList().add(listVo);
        }

        novelListVo.setTotal(total);
        novelListVo.setPageSize(pageSize);

        return new Response<>(1001, novelListVo);

    }

    @RequestMapping("novel/create")
    public Response createNovel(@RequestParam(name = "title") String title,
                                @RequestParam(name = "images") String images,
                                @RequestParam(name = "author") String author,
                                @RequestParam(name = "score", required = false, defaultValue = "0") Float score,
                                @RequestParam(name = "wordCount") Integer wordCount,
                                @RequestParam(name = "synopsis") String synopsis,
                                @RequestParam(name = "kindsId") BigInteger kindsId,
                                @RequestParam(name = "tags") String tags,
                                @RequestParam(name = "infoType") String infoType,
                                @RequestParam(name = "infoContent") String infoContent) {
//        if (BaseUtils.isEmpty(loginUser)) {
//            return new Response(1003);
//        }
        if (BaseUtils.isEmpty(title) || BaseUtils.isEmpty(images) || BaseUtils.isEmpty(author) | BaseUtils.isEmpty(synopsis) || BaseUtils.isEmpty(kindsId)) {
            return new Response<>(1002);
        }
        InfoVo infoVo = new InfoVo();
        infoVo.setType(infoType);
        infoVo.setContent(infoContent);
        String info = JSON.toJSONString(infoVo);
        BigInteger result = null;
        try {
            result = novelService.editNovel(null, title.trim(), images.trim(), author.trim(), score, wordCount, synopsis.trim(), kindsId, tags,info);
        } catch (Exception e) {
            log.info("系统异常");
        }
        return new Response<>(1001, "novelId=" + result);
    }

    @RequestMapping("/novel/update")
    public Response updateNovel(@RequestParam(name = "novelId") BigInteger novelId,
                                @RequestParam(name = "title", required = false) String title,
                                @RequestParam(name = "images", required = false) String images,
                                @RequestParam(name = "author", required = false) String author,
                                @RequestParam(name = "score", required = false, defaultValue = "0") Float score,
                                @RequestParam(name = "wordCount", required = false) Integer wordCount,
                                @RequestParam(name = "synopsis", required = false) String synopsis,
                                @RequestParam(name = "kindsId", required = false) BigInteger kindsId,
                                @RequestParam(name = "tags", required = false) String tags,
                                @RequestParam(name = "infoType") String infoType,
                                @RequestParam(name = "infoContent") String infoContent) {
//        if (BaseUtils.isEmpty(loginUser)) {
//            return new Response(1003);
//        }
        if (BaseUtils.isEmpty(novelId)) {
            return new Response<>(1002);
        }
        List<InfoVo> info = JSON.parseArray(novelService.getById(novelId).getInfo(), InfoVo.class);
        InfoVo infoVo = new InfoVo();
        infoVo.setType(infoType);
        infoVo.setContent(infoContent);
        info.add(infoVo);
        String infoStr = JSON.toJSONString(info);
        BigInteger result = null;
        try {
            result = novelService.editNovel(novelId, title.trim(), images.trim(), author.trim(), score, wordCount, synopsis.trim(), kindsId, tags,infoStr);
        } catch (RuntimeException e) {
            log.info("系统异常");
        }

        return new Response(1001, "novelId=" + result);

    }

    @RequestMapping("/novel/delete")
    public Response deleteNovel(@RequestParam(name = "novelId") BigInteger novelId,
                                @VerifiedUser User loginUser) {
        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1003);
        }
        int result = novelService.delete(novelId);
        return result == 1 ? new Response<>(1001) : new Response<>(1002);
    }

}
