package red.mlz.console.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String DELETE_KINDS_QUEUE = "delete.kinds.queue";

    @Bean
    public Queue deleteKindsQueue() {
        return new Queue(DELETE_KINDS_QUEUE);
    }
}