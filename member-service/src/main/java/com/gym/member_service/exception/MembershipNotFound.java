package com.gym.member_service.exception;

import com.gym.member_service.enums.MembershipType;

public class MembershipNotFound extends RuntimeException {
    public MembershipNotFound(MembershipType membershipType) {
        super("Membership not found: " + membershipType);
    }
}
