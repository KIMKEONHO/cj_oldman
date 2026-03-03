package com.example.cj_alimtalk_service.alimtalk.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 노인(대상자) SMS/알림톡 발송 대상 엔티티.
 * <p>
 * DB 테이블 {@code kw_oldman_sms}와 매핑되며, 복합 기본키 (site, cno, crtdt)는 {@link OldManId}로 매핑한다.
 * 스케줄러에서 조회 후 알림톡 API 수신자(ReceiverDto)로 가공해 사용한다.
 * </p>
 */
@Entity
@Table(name = "kw_oldman_sms")
@Getter
@NoArgsConstructor
public class OldMan {

    @EmbeddedId
    private OldManId oldManId;

    /** 대상자 고유 번호 */
    @Column(length = 20)
    private String mno;

    @Column(length = 10)
    private String sno;

    @Column(length = 10)
    private String sid;

    /** 대상자명 */
    @Column(length = 30)
    private String cname;

    /** 나이 */
    @Column(length = 30)
    private String cage;

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

    /** 알림톡 발송일 (yyyyMMdd) */
    @Column(length = 8)
    private String smsdate;

    public void setSmsdate(String smsdate) {
        this.smsdate = smsdate;
    }

    /** 문자 내용 */
    @Column(length = 255)
    private String smsmsg;

    @Column(length = 10)
    private Integer smstype;

    private LocalDateTime uptdt;

    // === 복합키 필드 편의 접근 (OldManId 내부 값) ===
    public String getSite() { return oldManId != null ? oldManId.getSite() : null; }
    public String getCno() { return oldManId != null ? oldManId.getCno() : null; }
    public LocalDateTime getCrtdt() { return oldManId != null ? oldManId.getCrtdt() : null; }
}