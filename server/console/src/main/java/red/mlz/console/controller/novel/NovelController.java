package red.mlz.console.controller.novel;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.config.FastJsonConfig;
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
import red.mlz.module.module.novelTagRelation.entity.NovelTagRelation;
import red.mlz.module.module.novelTagRelation.service.NovelTagRelationService;
import red.mlz.module.module.tags.entity.Tag;
import red.mlz.module.module.tags.service.TagsService;
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
    @Autowired
    private TagsService tagsService;
    @Autowired
    private NovelTagRelationService novelTagRelationService;

    @RequestMapping("novel/novelList")
    @Transactional(rollbackFor = Exception.class)
    public Response novelList(@VerifiedUser User loginUser,
                              @RequestParam(name="page")Integer page,
                              @RequestParam(name= "titleKeyWord",required = false)String titleKeyWord, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {

        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1003);
        }
        Integer pageSize = 4;
        List<String> kindsIdList = novelService.getKindsIdByKeyword(titleKeyWord);
        String ids = String.join(",", kindsIdList);
        Integer total = novelService.getNovelCount(titleKeyWord,ids);
        List<Novel> novelList = novelService.getNovelListByPage(page,pageSize,titleKeyWord,ids);
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

        return new Response<>(1001,novelListVo);

    }
    @RequestMapping("novel/create")
    public Response createNovel(@RequestParam(name = "title") String title,
                                @RequestParam(name = "images")String images,
                                @RequestParam(name = "author")String author,
                                @RequestParam(name = "score",required = false, defaultValue ="0")Float score,
                                @RequestParam(name = "wordCount")Integer wordCount,
                                @RequestParam(name = "synopsis")String synopsis,
                                @RequestParam(name = "kindsId")BigInteger kindsId,
                                @RequestParam(name= "tags")List<String> tags){
//        if (BaseUtils.isEmpty(loginUser)) {
//            return new Response(1003);
//        }
        if(BaseUtils.isEmpty(title)||BaseUtils.isEmpty(images)||BaseUtils.isEmpty(author)|BaseUtils.isEmpty(synopsis)||BaseUtils.isEmpty(kindsId)) {
           return new Response<>(1002);
        }
        if(kindsService.getKindsById(kindsId) != null){
            BigInteger novelId = null;
            BigInteger result = null;
        try{
            result=novelService.editNovel(novelId,title.trim(),images.trim(),author.trim(),score,wordCount,synopsis.trim(),kindsId,tags);
        } catch (Exception e)
        {
            log.info("系统异常");
        }
            return new Response<>(1001,"novelId="+result);
        }else {
            return new Response<>(3053);
        }
    }

    @RequestMapping("/novel/update")
    public Response updateNovel(@RequestParam(name = "novelId") BigInteger novelId,
                                @RequestParam(name = "title") String title,
                                @RequestParam(name = "images")String images,
                                @RequestParam(name = "author")String author,
                                @RequestParam(name = "score",required = false, defaultValue ="0")Float score,
                                @RequestParam(name = "wordCount")Integer wordCount,
                                @RequestParam(name = "synopsis")String synopsis,
                                @RequestParam(name = "kindsId")BigInteger kindsId,
                                @RequestParam(name= "tags")List<String> tags){
//        if (BaseUtils.isEmpty(loginUser)) {
//            return new Response(1003);
//        }
        if (BaseUtils.isEmpty(title)||BaseUtils.isEmpty(images)||BaseUtils.isEmpty(author)||BaseUtils.isEmpty(synopsis)||BaseUtils.isEmpty(kindsId)) {
            return new Response<>(1002);
        }
        BigInteger result = null;
        try {
            result=novelService.editNovel(novelId,title.trim(),images.trim(),author.trim(),score,wordCount,synopsis.trim(),kindsId,tags);
        } catch (RuntimeException e)
        {
            log.info("系统异常");
        }

        return new Response(1001,"novelId="+result);

    }
    @RequestMapping("/novel/delete")
    public Response deleteNovel(@RequestParam(name="novelId") BigInteger novelId,
                                @VerifiedUser User loginUser) {
        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1003);
        }
        int result = novelService.delete(novelId);
        return result == 1 ? new Response<>(1001) : new Response<>(1002);
    }
    @RequestMapping("/novel/getInfo")
    public Response getInfo(@RequestParam(name = "id")BigInteger id){
        List<InfoVo> info = JSON.parseArray(novelService.getNovelInfo(id), InfoVo.class);
        return new Response<>(1001,info);
    }
    @RequestMapping("/novel/addInfo")
    public Response addInfo(@RequestParam(name = "id")BigInteger id,
                            @RequestParam(name = "infoType")String infoType,
                            @RequestParam(name = "infoContent")String infoContent,
                            @VerifiedUser User loginUser){
        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1003);
        }
        List<InfoVo> info = JSON.parseArray(novelService.getNovelInfo(id), InfoVo.class);
        InfoVo infoVo = new InfoVo();
        infoVo.setType(infoType);
        infoVo.setContent(infoContent);
        info.add(infoVo);
        int result = novelService.updateInfo(id,JSON.toJSONString(info));
        return result == 1 ? new Response<>(1001) : new Response<>(1002);
    }
    @RequestMapping("/novel/deleteInfo")
    public Response deleteInfo(@RequestParam(name = "id")BigInteger id,
                               @RequestParam(name = "infoType")String infoType,
                               @VerifiedUser User loginUser){
        if (BaseUtils.isEmpty(loginUser)) {
            return new Response(1003);
        }
        List<InfoVo> info = JSON.parseArray(novelService.getNovelInfo(id), InfoVo.class);
        List<InfoVo> newInfo = new ArrayList<>();
        for (InfoVo infoVo : info) {
            if (!infoVo.getType().equals(infoType)) {
                newInfo.add(infoVo);
            }
        }
        novelService.updateInfo(id,JSON.toJSONString(info));
        return new Response<>(1001,newInfo);
    }

}
