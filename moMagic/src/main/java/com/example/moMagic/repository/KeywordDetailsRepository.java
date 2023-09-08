package com.example.moMagic.repository;

import com.example.moMagic.model.KeywordDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordDetailsRepository extends JpaRepository<KeywordDetails, Long> {
    KeywordDetails findByKeyword(String keyword);
}
