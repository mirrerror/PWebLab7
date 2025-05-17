package md.mirrerror.pweblab7.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import md.mirrerror.pweblab7.dtos.UserDto;
import md.mirrerror.pweblab7.dtos.mappers.UserMapper;
import md.mirrerror.pweblab7.exceptions.UserNotAuthenticatedException;
import md.mirrerror.pweblab7.exceptions.UserNotFoundException;
import md.mirrerror.pweblab7.models.User;
import md.mirrerror.pweblab7.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/users")
@RestController
@Tag(name = "Users", description = "Endpoints for managing users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/me")
    @Operation(summary = "Get authenticated user's info")
    public ResponseEntity<UserDto> authenticatedUser() {
        Optional<User> optionalUser = userService.getCurrentUser();

        if (optionalUser.isEmpty()) {
            throw new UserNotAuthenticatedException("You are not authenticated");
        }

        return ResponseEntity.ok(userMapper.mapToDto(optionalUser.get()));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users (Admin only)")
    public ResponseEntity<List<UserDto>> allUsers() {
        return ResponseEntity.ok(userMapper.mapToDtoList(userService.getAllUsers()));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user by ID (Admin only)")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userService.getUserById(id);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }

        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }

}
