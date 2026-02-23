package com.example.cj_alimtalk_service.alimtalk.service;

import com.example.cj_alimtalk_service.alimtalk.dto.response.ReceiverDto;
import com.example.cj_alimtalk_service.alimtalk.entity.OldMan;
import com.example.cj_alimtalk_service.alimtalk.repository.OldManRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * OldMan(노인/대상자) 도메인 서비스.
 * <p>
 * Repository를 통해 OldMan을 조회하고, 알림톡 API 수신자 포맷(ReceiverDto)으로 가공해 반환한다.
 * </p>
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class OldManService {

    private final OldManRepository oldManRepository;

    /**
     * 오늘 00:00 이후 등록된 OldMan을 조회한 뒤, 알림톡 수신자 포맷(ReceiverDto) 리스트로 변환해 반환한다.
     * <p>
     * 스케줄러 또는 API에서 알림 발송 대상 목록을 얻을 때 사용한다.
     * </p>
     *
     * @return 알림톡 API receiver 형식으로 변환된 수신자 목록
     */
    public List<ReceiverDto> getReceiversForAlim() {

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        List<OldMan> list = oldManRepository.findByCrtdtAfter(todayStart);
        return list.stream()
                .map(this::toReceiverDto)
                .toList();
    }

    /**
     * OldMan 한 건을 알림톡 API의 ReceiverDto로 변환한다.
     *
     * @param man 변환할 대상자 엔티티
     * @return 수신자 DTO (dstaddr, text, 템플릿 변수 등 설정됨)
     */
    private ReceiverDto toReceiverDto(OldMan man) {
        ReceiverDto dto = new ReceiverDto();
        dto.setDstaddr(man.getMgrphone());
        dto.setOptName(man.getCname());
        dto.setText("카카오 알림톡");
        dto.setText2("대체 발송 텍스트");
        dto.setKakaoVariable1("var1");
        dto.setKakaoVariable2(man.getAddress());
        dto.setKakaoVariable3("var3");
        return dto;
    }
}
