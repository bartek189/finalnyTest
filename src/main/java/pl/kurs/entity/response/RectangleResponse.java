package pl.kurs.entity.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RectangleResponse extends ShapeResponse {
    private double width;
    private double height;}
