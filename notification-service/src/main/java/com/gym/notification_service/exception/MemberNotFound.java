package com.gym.notification_service.exception;
public class MemberNotFound extends RuntimeException {
    public MemberNotFound(String idMember) {
        super("Member with id: "+ idMember + " not found. ");
    }
}

