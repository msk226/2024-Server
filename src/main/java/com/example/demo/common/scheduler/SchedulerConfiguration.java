package com.example.demo.common.scheduler;

import com.example.demo.src.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerConfiguration {

    private final UserService userService;

    // 매일 00시 00분 00초에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void updateStatus() {
        log.info("개인 정보 처리 동의 항목 유효성 검사 실행 ");
        // 개인 정보 처리 동의 항목 유효성 검사 -> 만료일이 지난 경우 NEEDS_CONSENT로 변경하여 재로그인 시 다시 동의 받도록 함
        userService.checkAllConsentRenewalDate();
    }

}
