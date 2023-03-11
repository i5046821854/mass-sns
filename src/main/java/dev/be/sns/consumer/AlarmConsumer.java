package dev.be.sns.consumer;

import dev.be.sns.model.event.AlarmEvent;
import dev.be.sns.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    private final AlarmService alarmService;

    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consumeAlarm(AlarmEvent event, Acknowledgment ack){
            alarmService.send(event.getAlarmType(), event.getAlarmArgs(), event.getReceiveUserId());
            ack.acknowledge();
    }
}
