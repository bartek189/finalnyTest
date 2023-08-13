package pl.kurs.entity.command;

import lombok.*;
import pl.kurs.entity.model.ERole;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateUserCommand {
    private String userName;
    private String password;
    private ERole roleName;
}
