package red.mlz.module.module.home.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import red.mlz.module.module.home.entity.Banner;
import red.mlz.module.module.home.entity.Channel;
import red.mlz.module.module.home.entity.Event;
import red.mlz.module.module.home.entity.Recommend;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class HomeService {
    @Async("homePageTaskExecutor")
    public CompletableFuture<List<Banner>> getBanners() {
        List<Banner> banners = new ArrayList<>();
        Banner banner1 = new Banner();
        Banner banner2 = new Banner();
        Banner banner3 = new Banner();
        banner1.setImage("图片1");
        banner1.setLink("链接1");
        banner2.setImage("图片2");
        banner2.setLink("链接2");
        banner3.setImage("图片3");
        banner3.setLink("链接3");
        banners.add(banner1);
        banners.add(banner2);
        banners.add(banner3);
        return CompletableFuture.completedFuture(banners);
    }
    @Async("homePageTaskExecutor")
    public CompletableFuture<List<Channel>> getChannels() {
        List<Channel> channels = new ArrayList<>();

        Channel channel1 = new Channel();
        channel1.setId(1);
        channel1.setImage("频道封面1.jpg");
        channel1.setTitle("热门推荐");

        Channel channel2 = new Channel();
        channel2.setId(2);
        channel2.setImage("频道封面2.jpg");
        channel2.setTitle("小说分类");

        Channel channel3 = new Channel();
        channel3.setId(3);
        channel3.setImage("频道封面3.jpg");
        channel3.setTitle("限时特惠");

        channels.add(channel1);
        channels.add(channel2);
        channels.add(channel3);
        return CompletableFuture.completedFuture(channels);
    }
    @Async("homePageTaskExecutor")
    public CompletableFuture<List<Event>> getEvents() {
        List<Event> events = new ArrayList<>();

        Event event1 = new Event();
        event1.setId(1);
        event1.setImage("活动海报1.jpg");
        event1.setTitle("新用户首充礼包");

        Event event2 = new Event();
        event2.setId(2);
        event2.setImage("活动海报2.jpg");
        event2.setTitle("周年庆限时活动");

        Event event3 = new Event();
        event3.setId(3);
        event3.setImage("活动海报3.jpg");
        event3.setTitle("周末特惠专场");

        events.add(event1);
        events.add(event2);
        events.add(event3);
        return CompletableFuture.completedFuture(events);
    }
    @Async("homePageTaskExecutor")
    public CompletableFuture<List<Recommend>> getRecommends() {
        List<Recommend> recommends = new ArrayList<>();

        Recommend recommend1 = new Recommend();
        recommend1.setId(1);
        recommend1.setImage("推荐封面1.jpg");
        recommend1.setTitle("编辑精选");

        Recommend recommend2 = new Recommend();
        recommend2.setId(2);
        recommend2.setImage("推荐封面2.jpg");
        recommend2.setTitle("畅销榜单");

        Recommend recommend3 = new Recommend();
        recommend3.setId(3);
        recommend3.setImage("推荐封面3.jpg");
        recommend3.setTitle("猜你喜欢");

        recommends.add(recommend1);
        recommends.add(recommend2);
        recommends.add(recommend3);
        return CompletableFuture.completedFuture(recommends);
    }
}
