package md.mirrerror.pweblab7.dtos.mappers;

import lombok.RequiredArgsConstructor;
import md.mirrerror.pweblab7.dtos.UserDto;
import md.mirrerror.pweblab7.exceptions.UserNotFoundException;
import md.mirrerror.pweblab7.models.User;
import md.mirrerror.pweblab7.services.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper implements IMapper<UserDto, User> {

    private final UserService userService;

    @Override
    public UserDto mapToDto(User user) {
        if (user == null) return null;

        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setEnabled(user.isEnabled());
        dto.setBirthDate(user.getBirthDate());

        return dto;
    }

    @Override
    public User mapToEntity(UserDto dto) {
        if (dto == null) return null;

        return userService.getUserById(dto.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + dto.getId()));
    }

    @Override
    public List<UserDto> mapToDtoList(List<User> entityList) {
        return entityList.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<User> mapToEntityList(List<UserDto> dtoList) {
        return dtoList.stream()
                .map(this::mapToEntity)
                .toList();
    }

}
