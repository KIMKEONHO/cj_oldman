package com.example.cj_alimtalk_service.alimtalk.runner;

import com.example.cj_alimtalk_service.alimtalk.dto.response.ReceiverDto;
import com.example.cj_alimtalk_service.alimtalk.service.AlimSendService;
import com.example.cj_alimtalk_service.alimtalk.service.OldManService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 앱 기동 시 알림톡 발송 로직을 한 번 실행하는 Runner.
 * <p>
 * cron으로 JAR를 실행할 때 웹 서버 없이 기동되며, 이 Runner가 실행된 뒤 프로세스가 종료된다.
 * </p>
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class AlimJobRunner implements CommandLineRunner {

    private final OldManService oldManService;
    private final AlimSendService alimSendService;

    @Override
    public void run(String... args) {
        log.info("알림톡 발송 작업 시작");
        List<ReceiverDto> receivers = oldManService.getReceiversForAlim();
        alimSendService.sendAlim(receivers);
        log.info("알림톡 발송 작업 완료");
        System.exit(0);
    }
}
