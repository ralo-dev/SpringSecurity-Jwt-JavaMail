package dev.ralo.example.authentication;

import lombok.Builder;

public record AuthenticationResponse(String token, String username, String role) {

    @Builder
    public AuthenticationResponse {
    }
}
