package com.example.moMagic.repository;

import com.example.moMagic.model.ChargeSuccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeSuccessLogRepository extends JpaRepository<ChargeSuccessLog, Long> {
}
