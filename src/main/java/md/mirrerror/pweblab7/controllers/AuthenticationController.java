package md.mirrerror.pweblab7.controllers;

import lombok.RequiredArgsConstructor;
import md.mirrerror.pweblab7.dtos.LoginUserDto;
import md.mirrerror.pweblab7.dtos.RegisterUserDto;
import md.mirrerror.pweblab7.models.User;
import md.mirrerror.pweblab7.responses.LoginResponse;
import md.mirrerror.pweblab7.services.AuthenticationService;
import md.mirrerror.pweblab7.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<RegisterUserDto> register(@RequestBody RegisterUserDto registerUserDto) {
        authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String token = jwtService.generateToken(authenticatedUser);
        long expiresIn = jwtService.getJwtExpirationTime();
        return ResponseEntity.ok(new LoginResponse(token, expiresIn));
    }

}
