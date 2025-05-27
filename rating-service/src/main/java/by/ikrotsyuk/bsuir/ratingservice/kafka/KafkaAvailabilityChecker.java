package by.ikrotsyuk.bsuir.ratingservice.kafka;

import by.ikrotsyuk.bsuir.ratingservice.kafka.producer.RatingProducer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

@RequiredArgsConstructor
@Component
@EnableScheduling
public class KafkaAvailabilityChecker {
    @Setter
    @Getter
    private boolean brokersAvailable = true;
    @Setter
    private RatingProducer ratingProducer;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(fixedRate = 60000)
    public void checkKafkaAvailability() {
        if(brokersAvailable)
            return;

        int minBrokersCount = Integer.parseInt(KafkaConstants.NUMBER_OF_INSYNC_REPLICAS_VALUE);

        Properties props = new Properties();
        String bootstrapServers = KafkaConstants.BOOTSTRAP_SERVERS;
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(props)){
            Collection<Node> nodes = adminClient.describeCluster().nodes().get();

            int activeBrokersCount = 0;
            for(Node node: nodes){
                try {
                    adminClient.describeConfigs(Collections.singleton(new ConfigResource(ConfigResource.Type.BROKER, String.valueOf(node.id())))).all().get();
                    activeBrokersCount++;
                } catch (Exception e) {
                    logger.error("Broker {} unavailable {}", node.id(), LocalDateTime.now());
                }
            }
            if(activeBrokersCount >= minBrokersCount) {
                setBrokersAvailable(true);
                ratingProducer.sendUnsentMessages();
            }
        } catch (Exception e) {
            logger.error("{} {}", e.getCause(), e.getMessage());
            setBrokersAvailable(false);
        }
    }
}
