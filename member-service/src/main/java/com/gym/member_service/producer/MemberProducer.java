package com.gym.member_service.producer;

import com.gym.member_service.events.NewMemberEvent;
import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberProducer {
    private final KafkaTemplate<String, NewMemberEvent> kafkaTemplate;

    public void sendNewMemberEvent(NewMemberEvent event) {
        kafkaTemplate.send("member-created-topic", event);
    }
}
