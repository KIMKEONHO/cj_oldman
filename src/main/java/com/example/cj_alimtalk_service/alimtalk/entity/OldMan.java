package com.example.cj_alimtalk_service.alimtalk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "kw_oldman_sms")
@Getter
@NoArgsConstructor
public class OldMan {

    @Id
    @Column(length = 20)
    private String mno;

    @Column(length = 20, nullable = false)
    private String site;

    @Column(length = 20)
    private String cno;

    @Column(length = 10)
    private String sno;

    @Column(length = 10)
    private String sid;

    @Column(length = 30)
    private String cname;

    private Short cage;

    @Column(length = 100)
    private String address;

    @Column(length = 100)
    private String addressdesc;

    @Column(length = 30)
    private String mgrname;

    @Column(length = 30)
    private String mgrtel;

    @Column(length = 30)
    private String mgrphone;

    @Column(length = 8)
    private String smsdate;

    private LocalDateTime crtdt;
}