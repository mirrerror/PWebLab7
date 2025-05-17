package md.mirrerror.pweblab7.dtos;

import lombok.Data;
import md.mirrerror.pweblab7.models.Role;

import java.time.LocalDate;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private LocalDate birthDate;

}
