package dev.ralo.example.authentication;

import dev.ralo.example.registration.ConfirmationTokenService;
import dev.ralo.example.registration.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final ConfirmationTokenService confirmationTokenService;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody RegistrationRequest registrationRequest) {
        try {
            authenticationService.register(registrationRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<Object> confirm(@RequestParam("token") String token) {
        try {
            confirmationTokenService.confirmToken(token);
            return ResponseEntity.ok("Thank you! Your account is now verified");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
