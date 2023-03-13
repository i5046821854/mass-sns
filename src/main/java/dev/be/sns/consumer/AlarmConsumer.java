package dev.be.sns.consumer;

import dev.be.sns.model.event.AlarmEvent;
import dev.be.sns.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmConsumer {

    private final AlarmService alarmService;

    @KafkaListener(topics = "${spring.kafka.topic.alarm}")
    public void consumeAlarm(AlarmEvent event, Acknowledgment ack){
        log.info("Consume the event {}", event);
        alarmService.send(event.getAlarmType(), event.getAlarmArgs(), event.getReceiveUserId());
        ack.acknowledge();
    }
}
