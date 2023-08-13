package pl.kurs.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.entity.model.User;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreatedShapesDto {

    private String userName;
    private int shapesSize;

    public UserCreatedShapesDto(User user) {
        this.userName = user.getUserName();
        this.shapesSize = user.getCreatedShape().size();
    }
}
