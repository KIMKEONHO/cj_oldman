package com.example.cj_alimtalk_service.alimtalk.service;

import com.example.cj_alimtalk_service.alimtalk.dto.response.ReceiverDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * OldMan 대상 알림톡 발송 스케줄러.
 * <p>
 * 매일 9시 21분에 오늘 등록된 대상자 목록을 조회해 알림톡 API로 전송한다.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class OldManSchedular {

    private final OldManService oldManService;
    private final AlimSendService alimSendService;

    /**
     * 매일 9시 21분에 실행된다.
     * 오늘 하루 등록된 OldMan을 수신자 포맷으로 가공한 뒤 알림톡 API로 전송한다.
     */
    @Scheduled(cron = "0 21 9 * * *")
    public void runAtNineTwentyOne() {
        List<ReceiverDto> receivers = oldManService.getReceiversForAlim();
        alimSendService.sendAlim(receivers);
    }
}
