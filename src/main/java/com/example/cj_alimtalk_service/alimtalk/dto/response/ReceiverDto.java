package com.example.cj_alimtalk_service.alimtalk.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * 알림톡 API 수신자 1명 분 DTO.
 * <p>
 * data.receiver 배열의 한 요소에 해당하며,
 * JSON 직렬화 시 dstaddr, opt_name, kakao_variable1 등 snake_case로 내보낸다.
 * </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReceiverDto {
    /** 수신자 전화번호 */
    private String dstaddr;
    /** 알림톡/친구톡 본문 */
    private String text;
    /** 대체 발송 시 사용할 문자 내용 */
    private String text2;
    /** 수신자명 (API 옵션) */
    private String optName;
    /** 템플릿 변수 1~3 (템플릿에 맞게 사용) */
    private String kakaoVariable1;
    private String kakaoVariable2;
    private String kakaoVariable3;
}