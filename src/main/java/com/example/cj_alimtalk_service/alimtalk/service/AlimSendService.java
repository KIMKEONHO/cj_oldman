package com.example.cj_alimtalk_service.alimtalk.service;

import com.example.cj_alimtalk_service.alimtalk.dto.response.AlimApiDataDto;
import com.example.cj_alimtalk_service.alimtalk.dto.response.AlimApiRequestDto;
import com.example.cj_alimtalk_service.alimtalk.dto.response.ReceiverDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 알림톡 API 발송 서비스.
 * <p>
 * 수신자 목록(ReceiverDto)을 받아 API 포맷(link_id, link_token, data)으로 조립한 뒤
 * 설정된 base-url로 POST 전송한다. 설정은 application-secret.yml의 alim.* 로 주입된다.
 * </p>
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AlimSendService {

    private final RestTemplate restTemplate;

    /** 알림톡 API 베이스 URL (application-secret) */
    @Value("${alim.api.base-url:}")
    private String baseUrl;

    @Value("${alim.api.link-id:}")
    private String linkId;

    @Value("${alim.api.link-token:}")
    private String linkToken;

    /** 발신번호 */
    @Value("${alim.data.callback:}")
    private String callback;

    @Value("${alim.data.dept-no:}")
    private String deptNo;

    /** 메시지 타입: kko_a(알림톡), kko_f(친구톡) */
    @Value("${alim.data.msg-type:kko_a}")
    private String msgType;

    @Value("${alim.data.k-template-code:}")
    private String kTemplateCode;

    @Value("${alim.data.kakao-dsptch-profl:}")
    private String kakaoDsptchProfl;

    /** 발송 실패 시 대체 발송: 1=카카오 재발송, 2=text2 재발송 */
    @Value("${alim.data.k-next:1}")
    private String kNext;

    /**
     * 수신자 목록을 알림톡 API 포맷으로 조립한 뒤 해당 주소로 전송한다.
     * <p>
     * 전송 실패 시 예외는 로그만 남기고 호출부로 전파하지 않는다.
     * </p>
     *
     * @param receivers 발송할 수신자 목록 (ReceiverDto)
     */
    public void sendAlim(List<ReceiverDto> receivers) {
        if (receivers.isEmpty()) {
            log.info("발송할 수신자가 없습니다.");
            return;
        }

        AlimApiDataDto data = new AlimApiDataDto();
        data.setCallback(callback);
        data.setDeptNo(deptNo);
        data.setMsgType(msgType);
        data.setKTemplateCode(kTemplateCode);
        data.setKakaoDsptchProfl(kakaoDsptchProfl);
        data.setKNext(kNext);
        data.setReceiver(receivers);

        AlimApiRequestDto request = new AlimApiRequestDto();
        request.setLinkId(linkId);
        request.setLinkToken(linkToken);
        request.setData(data);

        try {
            restTemplate.postForObject(baseUrl, request, String.class);
            log.info("알림톡 발송 요청 완료, 수신자 수: {}", receivers.size());
        } catch (RestClientException e) {
            log.error("알림톡 API 호출 실패. url={}, 수신자 수={}", baseUrl, receivers.size(), e);
        }
    }
}
