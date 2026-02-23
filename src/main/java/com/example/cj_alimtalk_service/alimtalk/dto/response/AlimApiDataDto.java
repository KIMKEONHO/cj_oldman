package com.example.cj_alimtalk_service.alimtalk.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

/**
 * 알림톡 API의 data 블록 DTO.
 * <p>
 * 발신번호, 부서번호, 템플릿 정보와 수신자 목록(receiver)을 담는다.
 * JSON 직렬화 시 snake_case로 내보낸다.
 * </p>
 *
 * @see ReceiverDto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AlimApiDataDto {
    /** 발신번호 */
    private String callback;
    /** 부서번호 */
    private String deptNo;
    /** 메시지 타입: kko_a(알림톡), kko_f(친구톡) */
    private String msgType;
    /** 알림톡 템플릿 코드 */
    private String kTemplateCode;
    /** 카카오 발신 프로필 (@프로필명) */
    private String kakaoDsptchProfl;
    /** 발송 실패 시 대체 발송 옵션: 1=카카오 재발송, 2=text2 재발송 */
    private String kNext;
    /** 수신자 목록 */
    private List<ReceiverDto> receiver;
}