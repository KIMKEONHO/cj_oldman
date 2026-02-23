package com.example.cj_alimtalk_service.alimtalk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 노인(대상자) SMS/알림톡 발송 대상 엔티티.
 * <p>
 * DB 테이블 {@code kw_oldman_sms}와 매핑되며,
 * 스케줄러에서 조회 후 알림톡 API 수신자(ReceiverDto)로 가공해 사용한다.
 * </p>
 */
@Entity
@Table(name = "kw_oldman_sms")
@Getter
@NoArgsConstructor
public class OldMan {

    /** 대상자 고유 번호 (PK) */
    @Id
    @Column(length = 20)
    private String mno;

    /** 사이트 구분 */
    @Column(length = 20, nullable = false)
    private String site;

    @Column(length = 20)
    private String cno;

    @Column(length = 10)
    private String sno;

    @Column(length = 10)
    private String sid;

    /** 대상자명 */
    @Column(length = 30)
    private String cname;

    /** 나이 */
    private Short cage;

    @Column(length = 100)
    private String address;

    @Column(length = 100)
    private String addressdesc;

    /** 담당자명 */
    @Column(length = 30)
    private String mgrname;

    /** 담당자 연락처(수신번호 후보) */
    @Column(length = 30)
    private String mgrtel;

    /** 담당자 휴대폰(수신번호 후보) */
    @Column(length = 30)
    private String mgrphone;

    @Column(length = 8)
    private String smsdate;

    /** 등록일시 (오늘 하루 조회 시 이 필드 기준 사용) */
    private LocalDateTime crtdt;
}