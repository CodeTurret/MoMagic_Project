package com.example.moMagic.repository;

import com.example.moMagic.model.ChargeFailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeFailLogRepository extends JpaRepository<ChargeFailLog, Long> {
}
