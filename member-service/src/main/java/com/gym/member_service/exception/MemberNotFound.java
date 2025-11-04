package com.gym.member_service.exception;

public class MemberNotFound extends RuntimeException {
  public MemberNotFound(String message) {
    super(message);
  }
}
