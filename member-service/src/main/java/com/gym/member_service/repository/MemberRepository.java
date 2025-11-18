package com.gym.member_service.repository;

import com.gym.member_service.enums.MembershipType;
import com.gym.member_service.models.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MemberRepository extends MongoRepository<Member, String> {
    Optional<Member> findByMembershipType(MembershipType membershipType);

}
