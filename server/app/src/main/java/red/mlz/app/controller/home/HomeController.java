package red.mlz.app.controller.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import red.mlz.module.module.home.entity.Banner;
import red.mlz.module.module.home.entity.Channel;
import red.mlz.module.module.home.entity.Event;
import red.mlz.module.module.home.entity.Recommend;
import red.mlz.module.module.home.service.HomeService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/home")
    public Map<String, Object> getHomeData() throws Exception {
        CompletableFuture<List<Banner>> banners = homeService.getBanners();
        CompletableFuture<List<Channel>> channels = homeService.getChannels();
        CompletableFuture<List<Event>> events = homeService.getEvents();
        CompletableFuture<List<Recommend>> recommends = homeService.getRecommends();

        CompletableFuture.allOf(banners, channels, events, recommends)
                .get(1, TimeUnit.HOURS); // 设置合理超时时间

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("banners", banners.get());
        result.put("channels", channels.get());
        result.put("events", events.get());
        result.put("recommends", recommends.get());

        return result;
    }
}
