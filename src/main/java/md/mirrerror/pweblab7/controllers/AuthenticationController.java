package md.mirrerror.pweblab7.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints for user authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @Operation(summary = "Register a new user")
    public ResponseEntity<RegisterUserDto> register(@RequestBody @Parameter(description = "User registration data") RegisterUserDto registerUserDto) {
        authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registerUserDto);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user and return JWT token")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Parameter(description = "User login credentials") LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String token = jwtService.generateToken(authenticatedUser);
        long expiresIn = jwtService.getJwtExpirationTime();
        return ResponseEntity.ok(new LoginResponse(token, expiresIn));
    }

}
