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

@Service
@Slf4j
@RequiredArgsConstructor
public class OldManService {

    private final OldManRepository oldManRepository;

    /**
     * OldMan 전체 조회 후 알림톡 수신자 포맷(ReceiverDto)으로 변환해 반환.
     */
    public List<ReceiverDto> getReceiversForAlim() {

        LocalDateTime todayStart = LocalDate.now().atStartOfDay();
        List<OldMan> list = oldManRepository.findByCrtdtAfter(todayStart);
        return list.stream()
                .map(this::toReceiverDto)
                .toList();
    }

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
