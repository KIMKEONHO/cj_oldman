package com.example.cj_alimtalk_service.alimtalk.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * OldMan 엔티티의 복합 기본키 (DB: PRIMARY KEY (site, cno, crtdt)).
 * @EmbeddedId 방식에서 사용.
 */
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OldManId implements Serializable {

    @Column(name = "site", length = 20, nullable = false)
    private String site;

    @Column(name = "cno", length = 20)
    private String cno;

    @Column(name = "crtdt")
    private LocalDateTime crtdt;
}
