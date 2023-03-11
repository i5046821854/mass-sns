package dev.be.sns.model.event;

import dev.be.sns.model.AlarmArgs;
import dev.be.sns.model.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmEvent {
    private Integer receiveUserId;
    private AlarmType alarmType;
    private AlarmArgs alarmArgs;

}
