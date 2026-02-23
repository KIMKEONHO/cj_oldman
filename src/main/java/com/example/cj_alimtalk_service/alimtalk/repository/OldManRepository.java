package com.example.cj_alimtalk_service.alimtalk.repository;

import com.example.cj_alimtalk_service.alimtalk.entity.OldMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OldMan(노인/대상자) 엔티티에 대한 JPA 리포지토리.
 * <p>
 * 알림톡 발송 대상 조회 시 등록일시(crtdt) 기준으로 기간 조회에 사용한다.
 * </p>
 */
@Repository
public interface OldManRepository extends JpaRepository<OldMan, String> {

    /**
     * 지정 시각 이후에 등록된 OldMan 목록을 조회한다.
     * <p>
     * 주로 "오늘 00:00 이후" 데이터만 조회할 때 사용한다.
     * </p>
     *
     * @param start 조회 시작 시각 (이 시각 이후 포함)
     * @return 조건에 맞는 OldMan 목록
     */
    List<OldMan> findByCrtdtAfter(LocalDateTime start);
}
