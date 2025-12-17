package jkucaba.springstore.scheduler;

import jkucaba.springstore.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionCleanupScheduler {

    private final SessionService sessionService;

    @Scheduled(fixedRate = 60_000)
    public void cleanupExpiredSessions() {
        sessionService.deleteExpiredSessions();
    }
}