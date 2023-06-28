package pl.kurs.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SquareResponse extends ShapeResponse {
    private double side;
}
