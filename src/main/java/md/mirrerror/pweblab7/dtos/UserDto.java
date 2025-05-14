package md.mirrerror.pweblab7.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String email;
    private boolean isEnabled;
    private LocalDate birthDate;

}
