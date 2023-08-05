package dev.ralo.example.email;

import dev.ralo.example.registration.ConfirmationToken;
import org.springframework.scheduling.annotation.Async;

public interface EmailSender {
    @Async
    void send(ConfirmationToken confirmationToken);
}
