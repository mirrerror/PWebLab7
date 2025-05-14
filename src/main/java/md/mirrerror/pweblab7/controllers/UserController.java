package md.mirrerror.pweblab7.controllers;

import lombok.RequiredArgsConstructor;
import md.mirrerror.pweblab7.dtos.UserDto;
import md.mirrerror.pweblab7.dtos.mappers.UserMapper;
import md.mirrerror.pweblab7.exceptions.UserNotAuthenticatedException;
import md.mirrerror.pweblab7.models.User;
import md.mirrerror.pweblab7.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserDto> authenticatedUser() {
        Optional<User> optionalUser = userService.getCurrentUser();

        if (optionalUser.isEmpty()) {
            throw new UserNotAuthenticatedException("You are not authenticated");
        }

        return ResponseEntity.ok(userMapper.mapToDto(optionalUser.get()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> allUsers() {
        return ResponseEntity.ok(userMapper.mapToDtoList(userService.getAllUsers()));
    }

}
