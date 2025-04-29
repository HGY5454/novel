package red.mlz.app.crond;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.app.domain.novel.*;
import red.mlz.module.module.novel.entity.Novel;
import red.mlz.module.module.novel.service.NovelService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class NovelCrond {
    @Autowired
    private NovelService novelService;

    @Scheduled(cron = "0 0/30 9-17 * * MON-FRI")
    public NovelListVo getNovelList() {
        List<Novel> novelListVo = novelService.getAllNovel();

        NovelListVo novelListVoList = new NovelListVo();

        for (Novel novel : novelListVo) {
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
                ar = (float) w / h;
            } else {
                System.out.println("No match found");
            }
            image.setUrl(imageUrl);
            image.setAr(ar);
            listVo.setImage(image);
            listVo.setAuthor(novel.getAuthor());

            novelListVoList.getList().add(listVo);
        }
        return novelListVoList;
    }
}
