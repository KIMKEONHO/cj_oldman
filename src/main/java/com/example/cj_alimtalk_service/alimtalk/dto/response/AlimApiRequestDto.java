package com.example.cj_alimtalk_service.alimtalk.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

/**
 * 알림톡 API 최상위 요청 DTO.
 * <p>
 * JSON 직렬화 시 키는 snake_case(link_id, link_token 등)로 내보낸다.
 * </p>
 *
 * @see AlimApiDataDto
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AlimApiRequestDto {
    /** API 인증용 링크 아이디 */
    private String linkId;
    /** API 인증용 링크 토큰 */
    private String linkToken;
    /** 발송 공통 정보 + 수신자 목록 */
    private AlimApiDataDto data;
}
