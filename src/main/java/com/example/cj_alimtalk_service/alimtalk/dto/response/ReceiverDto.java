package com.example.cj_alimtalk_service.alimtalk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ReceiverDto {
    private String dstaddr;
    private String text;
    private String text2;
    private String optName;
    private String kakaoVariable1;
    private String kakaoVariable2;
    private String kakaoVariable3;
}