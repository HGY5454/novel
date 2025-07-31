package red.mlz.console.consumer;

import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import red.mlz.module.module.novel.entity.Novel;
import red.mlz.module.module.novel.service.NovelService;

import java.math.BigInteger;
import java.util.List;

@Component
@Log4j2
public class KindsDeleteConsumer {

    @Autowired
    private NovelService novelService;

    @RabbitListener(queues = "delete.kinds.queue")
    public void processDeleteKinds(BigInteger kindsId) {
        try {
            List<Novel> novelList = novelService.getNovelByKindsId(kindsId);
            for (Novel novel : novelList) {
                BigInteger novelKindsId = novel.getKindsId();
                novelService.delete(novelKindsId);
                log.info("Deleted novel with kindsId: {}", novelKindsId);
            }
        } catch (Exception e) {
            log.error("Error processing delete kinds message for kindsId: {}", kindsId, e);
        }
    }
}