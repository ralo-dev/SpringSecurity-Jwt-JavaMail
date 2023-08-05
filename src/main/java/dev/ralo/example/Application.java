package dev.ralo.example;

import dev.ralo.example.user.UserEntity;
import dev.ralo.example.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class Application {

    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            log.info("Checkout for more Spring Boot examples at https://github.com/ralo-dev");
        };
    }

    @PostConstruct
    public void init() {
        UserEntity admin = UserEntity.builder()
                .username("admin")
                .password("admin")
                .role("ROLE_ADMIN")
                .build();
        UserEntity user = UserEntity.builder()
                .username("user")
                .password("user")
                .role("ROLE_USER")
                .build();

            userService.save(admin);
            userService.save(user);
            log.info("""
                \n
                ========================================
                Admin user created with credentials:
                username: admin
                password: admin
                ========================================
                User user created with credentials:
                username: user
                password: user
                ========================================
                """
            );
    }

}
