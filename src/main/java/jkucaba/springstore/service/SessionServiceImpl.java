package jkucaba.springstore.service;

import jkucaba.springstore.entity.Session;
import jkucaba.springstore.entity.User;
import jkucaba.springstore.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;

    private static final Duration SESSION_TTL = Duration.ofMinutes(20);

    @Override
    public Session createSession(User user) {

        Instant expiresAt = Instant.now().plus(SESSION_TTL);
        return sessionRepository.save(
                Session.create(user, expiresAt)
        );
    }

    @Override
    public User validateSession(UUID sessionId) {
        Session session = sessionRepository.findByIdAndExpiresAtAfter(
                sessionId, Instant.now()
        ).orElseThrow(() -> new RuntimeException("Invalid or expired session"));

        return session.getUser();
    }

    @Override
    public void deleteExpiredSessions() {
        sessionRepository.deleteByExpiresAtBefore(Instant.now());
    }
}
