package jkucaba.springstore.repository;

import jakarta.validation.ConstraintViolationException;
import jkucaba.springstore.bootstrap.BootstrapData;
import jkucaba.springstore.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import(BootstrapData.class)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    void saveUser_shouldFlushUserToRepository(){
        User savedUser = userRepository.save(
                User.builder()
                        .email("testemail@gmail.com")
                        .passwordHash(encoder.encode("testpassword"))
                        .build()
        );

        userRepository.flush();

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertThat("testemail@gmail.com").isEqualTo(savedUser.getEmail());
    }

    @Test
    void saveUserWithInvalidEmail_shouldRespondWithError(){
        assertThrows(ConstraintViolationException.class, () -> {
            User savedUser = userRepository.save(
                            User.builder()
                                    .email("testemail")
                                    .passwordHash(encoder.encode("testpassword"))
                                    .build()
                    );
            userRepository.flush();
                }
        );
    }

}