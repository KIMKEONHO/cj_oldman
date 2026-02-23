package com.example.cj_alimtalk_service.alimtalk.repository;

import com.example.cj_alimtalk_service.alimtalk.entity.OldMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OldManRepository extends JpaRepository<OldMan, String> {

    List<OldMan> findByCrtdtAfter(LocalDateTime start);
}
