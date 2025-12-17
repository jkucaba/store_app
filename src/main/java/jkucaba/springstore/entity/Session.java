package jkucaba.springstore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Table(name = "sessions")
@Entity
@NoArgsConstructor
@Getter
public class Session {

    @Id
    @UuidGenerator
    @Column(columnDefinition = "BINARY(16)", nullable = false, updatable = false)
    @JdbcTypeCode(SqlTypes.BINARY)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    public static Session create(User user, Instant expiresAt) {
        Session session = new Session();
        session.user = user;
        session.expiresAt = expiresAt;
        return session;
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }
}
