package dev.ralo.example.authentication;

import dev.ralo.example.email.EmailService;
import dev.ralo.example.jwt.JwtService;
import dev.ralo.example.registration.ConfirmationToken;
import dev.ralo.example.registration.ConfirmationTokenService;
import dev.ralo.example.registration.RegistrationRequest;
import dev.ralo.example.user.UserEntity;
import dev.ralo.example.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmailService emailService;
    private final ConfirmationTokenService confirmationTokenService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authenticationRequest.username(),
                    authenticationRequest.password()
                )
            );
            log.info("User authenticated: {}", authenticationRequest.username());
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return AuthenticationResponse.builder()
                    .token(jwtService.generateToken(userDetails))
                    .username(userDetails.getUsername())
                    .role(userDetails.getAuthorities().toString())
                    .build();
        } catch (DisabledException e) {
            log.warn("Authentication failed because user is disabled: {}", authenticationRequest.username());
            throw new DisabledException("User disabled", e);
        } catch (LockedException e) {
            log.warn("Authentication failed because user is locked: {}", authenticationRequest.username());
            throw new LockedException("User locked", e);
        } catch (BadCredentialsException e) {
            log.warn("Authentication failed because of bad credentials: {}", authenticationRequest.username());
            throw new BadCredentialsException("Bad credentials", e);
        } catch (Exception e) {
            log.error("Authentication failed: {}", e.getMessage());
            throw new RuntimeException("Error authenticating user", e);
        }
    }

    @Transactional
    public void register(RegistrationRequest registrationRequest) {
        //TODO: add validation logic for password, email, etc.
        UserEntity user = userService.save(UserEntity.builder()
                .username(registrationRequest.username())
                .password(registrationRequest.password())
                .role(registrationRequest.role() == null ? "ROLE_USER" : registrationRequest.role())
                .email(registrationRequest.email())
                .build()
        );
        ConfirmationToken confirmationToken = confirmationTokenService.generateToken(user);
        emailService.send(confirmationToken);
    }
}
