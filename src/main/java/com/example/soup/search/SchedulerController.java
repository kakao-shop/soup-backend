package com.example.soup.search;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService schedulerService;
    private final TestService testService;

    @Scheduled(cron = "* * * * * *") // 메소드 호출이 종료되는 시간에서 10000ms 이후 재 호출
    void doTestJob() {
        schedulerService.job();
        // value 어노테이션으로 값 잘 들어왔는지 테스트

    }

}
