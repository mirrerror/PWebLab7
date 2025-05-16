package md.mirrerror.pweblab7.services;

import lombok.RequiredArgsConstructor;
import md.mirrerror.pweblab7.exceptions.UserNotAuthenticatedException;
import md.mirrerror.pweblab7.models.User;
import md.mirrerror.pweblab7.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) return Optional.of((User) authentication.getPrincipal());
        return Optional.empty();
    }

    public Optional<User> loadCurrentUser() {
        return getUserById(getCurrentUser().orElseThrow(() -> new UserNotAuthenticatedException("The user is not authenticated")).getId());
    }

}
