package com.example.moMagic.repository;

import com.example.moMagic.model.AppConf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppConfRepository extends JpaRepository<AppConf, Long> {
    AppConf findFirstByOrderById();
}
