package dev.ralo.example.registration;

import dev.ralo.example.user.UserEntity;
import dev.ralo.example.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenReposiroty confirmationTokenReposiroty;
    private final UserService userService;

    public ConfirmationToken getToken(String token) {
        return confirmationTokenReposiroty.findByToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
    }

    public ConfirmationToken getTokenByUsername(String username) {
        return confirmationTokenReposiroty.findByUserEntityUsername(username)
                .orElseThrow(() -> new IllegalStateException("Token not found"));
    }

    public ConfirmationToken generateToken(UserEntity userEntity) {
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .userEntity(userEntity)
                .build();
        return confirmationTokenReposiroty.save(confirmationToken);
    }

    public void confirmToken (String token) {
        ConfirmationToken confirmationToken = getToken(token);
        if (confirmationToken.isVerified()) {
            throw new IllegalStateException("Email already verified");
        }
        if (confirmationToken.isExpired()) {
            throw new IllegalStateException("Token expired");
        }
        confirmationToken.setVerifiedAt(LocalDateTime.now());
        confirmationTokenReposiroty.save(confirmationToken);
        userService.enableUser(confirmationToken.getUserEntity().getUsername());
    }
}
