package com.gym.member_service.repository;

import com.gym.member_service.models.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberRepository extends MongoRepository<Member, String> {
}
