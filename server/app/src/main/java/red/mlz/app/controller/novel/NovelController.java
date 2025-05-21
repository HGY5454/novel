package red.mlz.app.controller.novel;


import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.app.annotations.VerifiedUser;
import red.mlz.app.domain.novel.*;
import red.mlz.module.module.kinds.entity.Kinds;
import red.mlz.module.module.kinds.service.KindsService;
import red.mlz.module.module.novel.entity.Novel;
import red.mlz.module.module.novel.service.NovelService;
import red.mlz.module.module.user.entity.User;
import red.mlz.module.module.user.service.UserService;
import red.mlz.module.utils.BaseUtils;
import red.mlz.module.utils.Response;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@Log4j2
public class NovelController {

    @Autowired
    private NovelService novelService;
    @Autowired
    private KindsService kindsService;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/novel/list")
    public Response novelList(@RequestParam(name = "wp", required = false) String wp,
                              @RequestParam(name = "keyWord", required = false, defaultValue = "%") String keyWord) {

        Integer page;
        Integer pageSize = 4;
        WpVo localWp = new WpVo();
        String encodedString;

        if (wp == null) {
            page = 1;
            localWp.setPage(page + 1);
            if (keyWord != null) {
                localWp.setKeyword(keyWord);
            }
            encodedString = BaseUtils.objectToString(localWp);
        } else {
            localWp = BaseUtils.stringToObject(wp, localWp);
            page = localWp.getPage();
            keyWord = localWp.getKeyword();
            localWp.setPage(page + 1);
            encodedString = BaseUtils.objectToString(localWp);
        }

        String novelListVoCacheKey = String.format("novel:listVo:%s:%d:%d", keyWord, page, pageSize);

        NovelListVo cachedNovelListVo = (NovelListVo) redisTemplate.opsForValue().get(novelListVoCacheKey);
        if (cachedNovelListVo != null) {
            cachedNovelListVo.setWp(encodedString);
            return new Response<>(1001, cachedNovelListVo);
        }

        List<Novel> novelList = novelService.getNovelListByPage(page, pageSize, keyWord, null);

        List<BigInteger> kindIds = novelList.stream()
                .map(Novel::getKindsId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());

        Map<BigInteger, Kinds> kindsMap = kindsService.getKindsMapByIds(kindIds);

        NovelListVo novelListVo = new NovelListVo();
        novelListVo.setList(new ArrayList<>());

        for (Novel novel : novelList) {
            ListVo listVo = new ListVo();
            listVo.setScore(novel.getScore());
            listVo.setTitle(novel.getTitle());
            listVo.setInfo(JSON.parseArray(novel.getInfo(), InfoVo.class));

            Image image = new Image();
            String imageUrl = novel.getImagesUrl().split("\\$")[0];
            Matcher matcher = Pattern.compile(".*?_(\\d+)x(\\d+)").matcher(imageUrl);
            float ar = matcher.find() ?
                    (float) Integer.parseInt(matcher.group(1)) / Integer.parseInt(matcher.group(2)) : 0f;
            image.setUrl(imageUrl);
            image.setAr(ar);
            listVo.setImage(image);

            listVo.setAuthor(novel.getAuthor());
            if (kindsMap.containsKey(novel.getKindsId())) {
                listVo.setKindsName(kindsMap.get(novel.getKindsId()).getKindsName());
                novelListVo.getList().add(listVo);
            }
        }

        novelListVo.setWp(encodedString);
        novelListVo.setIsEnd(novelList.size() < pageSize);

        redisTemplate.opsForValue().set(novelListVoCacheKey, novelListVo, 10, TimeUnit.MINUTES);

        return new Response<>(1001, novelListVo);
    }



    @RequestMapping("/novel/info")
    public Response novelInfo(@RequestParam(name="novelId") BigInteger novelId) {

        Novel novel = novelService.getById(novelId);
        NovelVo novelVo = new NovelVo();

        if(novel == null){
            return null;
        }else {
            novelVo.setNovelId(novel.getId());
            novelVo.setTitle(novel.getTitle());
            novelVo.setImages(new ArrayList<>(Arrays.asList(novel.getImagesUrl().split("\\$"))));
            novelVo.setAuthor(novel.getAuthor());
            novelVo.setScore(novel.getScore());
            Kinds kinds = kindsService.getKindsById(novel.getKindsId());
            novelVo.setKindsName(kinds.getKindsName());
            novelVo.setKindsImage(kinds.getKindsImage());
            novelVo.setWordCount(novel.getWordCount());
            novelVo.setSynopsis(novel.getSynopsis());
            novelVo.setInfo(JSON.parseArray(novel.getInfo(),InfoVo.class));
        }
        return new Response<>(1001,novelVo);
    }

    @RequestMapping("/novel/kinds")
    public Response Kinds(){

        List<Kinds> kinds = kindsService.getKinds();
        List<Kinds> parentKinds = kindsService.getParentKinds();
        List<KindsListVo> kindsListVos = new ArrayList<>();

        for (Kinds kindParent : parentKinds) {
            KindsListVo childrenListVo = new KindsListVo();
            childrenListVo.setKindsList(new ArrayList<>());
            childrenListVo.setKindsName(kindParent.getKindsName());
            childrenListVo.setKindsImages(kindParent.getKindsImage());
            childrenListVo.setId(kindParent.getId());
            for (Kinds kind : kinds){
                if (kind.getParentId().equals(kindParent.getId().intValue())){
                    KindsVo kindsVo = new KindsVo();
                    kindsVo.setKindsName(kind.getKindsName());
//                    kindsVo.setKindsImage(kind.getKindsImage());
                    kindsVo.setId(kind.getId());
                    childrenListVo.getKindsList().add(kindsVo);
                }
            }
            kindsListVos.add(childrenListVo);
        }

        return new Response<>(1001,kindsListVos);
    }
    @RequestMapping("/novel/children_kinds")
    public Response ChildrenKinds(@RequestParam(name = "kindsId")BigInteger kindsId){

        ChildrenKindsVo childrenKindsVo = new ChildrenKindsVo();
        List<Kinds> childrenKinds = kindsService.getChildKinds(kindsId);
        List<KindsVo> kinds = new ArrayList<>();
        List<BigInteger> childrenKindsId = new ArrayList<>();

        for (Kinds kind : childrenKinds) {
            KindsVo kindsVo = new KindsVo();
            kindsVo.setKindsName(kind.getKindsName());

            Image image = new Image();
            String imageUrl = kind.getKindsImage();
            String regex = ".*?_(\\d+)x(\\d+)";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(imageUrl);
            float ar = 0;
            if (matcher.find()) {
                int w = Integer.parseInt(matcher.group(1));
                int h = Integer.parseInt(matcher.group(2));
                ar = (float) w/h;
            } else {
                System.out.println("No match found");
            }

            image.setUrl(imageUrl);
            image.setAr(ar);
            kindsVo.setKindsImage(image);
            kindsVo.setId(kind.getId());
            childrenKindsId.add(kind.getId());
            kinds.add(kindsVo);
        }
        childrenKindsVo.setKindsVoList(kinds);

        List<Novel> novelList = new ArrayList<>();
        for (BigInteger childrenId : childrenKindsId){
            List<Novel> novelListTMP = novelService.getNovelByKindsId(childrenId);
            novelList.addAll(novelListTMP);
        }
        List<ListVo> novelListVo = new ArrayList<>();
        for (Novel novel : novelList) {
            ListVo listVo = new ListVo();
            listVo.setScore(novel.getScore());
            listVo.setTitle(novel.getTitle());

            Image image = new Image();
            List<String> images = new ArrayList<>(Arrays.asList(novel.getImagesUrl().split("\\$")));
            String imageUrl = images.get(0);
            String regex = ".*?_(\\d+)x(\\d+)";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(imageUrl);
            float ar = 0;
            if (matcher.find()) {
                int w = Integer.parseInt(matcher.group(1));
                int h = Integer.parseInt(matcher.group(2));
                ar = (float) w/h;
            } else {
                System.out.println("No match found");
            }

            image.setUrl(imageUrl);
            image.setAr(ar);

            listVo.setImage(image);
            listVo.setAuthor(novel.getAuthor());

            novelListVo.add(listVo);
        }
        childrenKindsVo.setNovelListVoList(novelListVo);

        return new Response<>(1001,childrenKindsVo);
    }
}
