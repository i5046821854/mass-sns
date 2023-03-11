package dev.be.sns.producer;

import dev.be.sns.model.event.AlarmEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmProducer {

    public KafkaTemplate<Integer, AlarmEvent> kafkaTemplate;
    @Value("${spring.kafka.topic.alarm}")
    private String topic;

    public void send(AlarmEvent event){
        kafkaTemplate.send(topic,event.getReceiveUserId(),event );
    }
}
