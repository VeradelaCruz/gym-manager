package com.gym.payment_service.exeption;

public class MemberNotFound extends RuntimeException {
    public MemberNotFound(String idMember) {
        super( "Member with id: " + idMember + " not found");
    }
}
