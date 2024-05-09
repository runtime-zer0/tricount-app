package runtimezero.tricount.repository;


import runtimezero.tricount.entity.Settlement;

import java.util.Optional;

public interface SettlementRepository {
    Settlement create(String name);

    void addParticipantToSettlement(Long settlementId, Long memberId);

    Optional<Settlement> findById(Long id);
}
