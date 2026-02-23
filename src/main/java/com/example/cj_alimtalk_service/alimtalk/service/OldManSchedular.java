package com.example.cj_alimtalk_service.alimtalk.service;

import com.example.cj_alimtalk_service.alimtalk.dto.response.ReceiverDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OldManSchedular {

    private final OldManService oldManService;
    private final AlimSendService alimSendService;

    @Scheduled(cron = "0 13 17 * * *")
    public void runAtNineTwentyOne() {
        List<ReceiverDto> receivers = oldManService.getReceiversForAlim();
        alimSendService.sendAlim(receivers);
    }
}
