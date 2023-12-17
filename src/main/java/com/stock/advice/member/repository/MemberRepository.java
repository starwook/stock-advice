package com.stock.advice.member.repository;

import com.stock.advice.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {

    @Query("select m from Member m where m.memberId = :memberId")
    Optional<Member> findMemberByMemberId(@Param("memberId") String memberId);
}
