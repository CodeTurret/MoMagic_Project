package com.example.moMagic.repository;

import com.example.moMagic.model.ChargeConf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeConfRepository extends JpaRepository<ChargeConf, Long> {
    ChargeConf findByPrice(int price);
}
