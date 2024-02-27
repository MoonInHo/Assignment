package com.innovation.assignment.member.domain.repository;

import com.innovation.assignment.member.domain.entity.Member;
import com.innovation.assignment.member.infrastructure.repository.MemberQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {
}
