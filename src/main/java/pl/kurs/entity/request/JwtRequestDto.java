package pl.kurs.entity.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JwtRequestDto {

    @NotBlank
    private String userName;
    @NotBlank
    private String password;

}
