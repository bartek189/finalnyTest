package pl.kurs.entity.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FindShapeQuery {

    private String created_By;
    private String type;
    private Double areaFrom;
    private Double areaTo;
    private Double perimeterFrom;
    private Double perimeterTo;
    private Double side;
    private Double radius;
    private Double height;
    private Double width;


}