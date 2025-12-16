package jkucaba.springstore.model;

import java.util.UUID;

public record LoginResponse(
        UUID sessionId
) {}
