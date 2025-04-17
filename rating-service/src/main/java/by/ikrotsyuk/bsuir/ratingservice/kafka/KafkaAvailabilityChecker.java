package by.ikrotsyuk.bsuir.ratingservice.kafka;

import by.ikrotsyuk.bsuir.ratingservice.kafka.producer.impl.RatingProducer;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.config.ConfigResource;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;
    @Setter
    @Getter
    private boolean brokersAvailable = true;
    @Setter
    private RatingProducer ratingProducer;

    @Scheduled(fixedRate = 60000)
    public void checkKafkaAvailability() {
        if(brokersAvailable)
            return;
        System.out.println();
        System.err.println("SCHEDULER THREAD NAME:");
        System.err.println(Thread.currentThread().getName());
        System.out.println();

        int minBrokersCount = Integer.parseInt(KafkaConstants.NUMBER_OF_INSYNC_REPLICAS_VALUE);

        Properties props = new Properties();
        props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(props)){
            Collection<Node> nodes = adminClient.describeCluster().nodes().get();

            int activeBrokersCount = 0;
            for(Node node: nodes){
                try {
                    adminClient.describeConfigs(Collections.singleton(new ConfigResource(ConfigResource.Type.BROKER, String.valueOf(node.id())))).all().get();
                    activeBrokersCount++;
                } catch (Exception e) {
                    System.err.println("Broker " + node.id() + " unavailable" + LocalDateTime.now());
                }
            }
            System.out.println();
            System.err.println("BROKERS COUNT FAIL");
            System.out.println();
            if(activeBrokersCount >= minBrokersCount) {
                setBrokersAvailable(true);
                ratingProducer.sendUnsentMessages();
            }
        } catch (Exception e) {
            System.err.println(e.getCause() + e.getMessage());
            setBrokersAvailable(false);
        }
    }
}
