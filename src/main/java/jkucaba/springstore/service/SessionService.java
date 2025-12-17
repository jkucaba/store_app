package jkucaba.springstore.service;

import jkucaba.springstore.entity.Session;
import jkucaba.springstore.entity.User;

import java.util.UUID;

public interface SessionService {

    Session createSession(User user);

    User validateSession(UUID sessionId);

    Session validateSessionEntity(UUID sessionId);

    void deleteExpiredSessions();

}
