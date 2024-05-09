package runtimezero.tricount.repository;


import runtimezero.tricount.entity.Member;

import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    Optional<Member> findById(Long id);
    Optional<Member> findByLoginId(String loginId);
}
