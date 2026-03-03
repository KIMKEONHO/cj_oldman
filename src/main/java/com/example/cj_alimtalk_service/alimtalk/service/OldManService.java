package com.example.cj_alimtalk_service.alimtalk.service;

import com.example.cj_alimtalk_service.alimtalk.dto.response.ReceiverDto;
import com.example.cj_alimtalk_service.alimtalk.entity.OldMan;
import com.example.cj_alimtalk_service.alimtalk.repository.OldManRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
     * 오늘 00:00 이후 등록된 OldMan 목록을 조회한다.
     *
     * @return 알림톡 발송 대상 OldMan 목록
     */
    public List<OldMan> getOldMenForAlim() {
        LocalDate now = LocalDate.now();

        String todayStr = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        log.info("변환 날짜 : " + todayStr);

        return oldManRepository.findBySmsdate("20260304");
    }

    /**
     * 오늘 00:00 이후 등록된 OldMan을 조회한 뒤, 알림톡 수신자 포맷(ReceiverDto) 리스트로 변환해 반환한다.
     *
     * @return 알림톡 API receiver 형식으로 변환된 수신자 목록
     */
    public List<ReceiverDto> getReceiversForAlim() {
        return getOldMenForAlim().stream()
                .map(this::toReceiverDto)
                .collect(Collectors.toList());
    }

    /**
     * 전달된 OldMan 목록을 ReceiverDto 목록으로 변환한다.
     */
    public List<ReceiverDto> getReceiversFromOldMen(List<OldMan> oldMen) {
        return oldMen.stream()
                .map(this::toReceiverDto)
                .collect(Collectors.toList());
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
        dto.setOptName(man.getMgrname());
        dto.setText("카카오 알림톡");
        dto.setText2("대체 발송 텍스트");
        dto.setKakaoVariable1(man.getSmsmsg());
        return dto;
    }
}
