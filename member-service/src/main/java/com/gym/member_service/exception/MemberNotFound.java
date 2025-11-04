package com.gym.member_service.exception;

public class MemberNotFound extends RuntimeException {
    public MemberNotFound(String idMember) {
        super("Member with id: "+ idMember + " not found. ");
    }
}
