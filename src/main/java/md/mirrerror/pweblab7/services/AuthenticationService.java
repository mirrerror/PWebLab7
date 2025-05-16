package md.mirrerror.pweblab7.services;

import lombok.RequiredArgsConstructor;
import md.mirrerror.pweblab7.dtos.LoginUserDto;
import md.mirrerror.pweblab7.dtos.RegisterUserDto;
import md.mirrerror.pweblab7.exceptions.InvalidCredentialsException;
import md.mirrerror.pweblab7.exceptions.UserNotFoundException;
import md.mirrerror.pweblab7.exceptions.UserWithThisEmailAlreadyExistsException;
import md.mirrerror.pweblab7.models.Role;
import md.mirrerror.pweblab7.models.User;
import md.mirrerror.pweblab7.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User signUp(RegisterUserDto input) {
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());

        if (optionalUser.isPresent()) {
            throw new UserWithThisEmailAlreadyExistsException("A user with this email already exists");
        }

        User user = new User(input.getUsername(), passwordEncoder.encode(input.getPassword()), input.getEmail());
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword()));
        } catch (BadCredentialsException ignored) {
            throw new InvalidCredentialsException("Invalid email and/or password");
        }

        return user;
    }

}
