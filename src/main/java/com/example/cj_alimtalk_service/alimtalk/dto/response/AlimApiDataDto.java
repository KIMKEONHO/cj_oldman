package com.example.cj_alimtalk_service.alimtalk.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AlimApiDataDto {
    private String callback;
    private String deptNo;
    private String msgType;
    private String kTemplateCode;
    private String kakaoDsptchProfl;
    private String kNext;
    private List<ReceiverDto> receiver;
}