package pl.kurs.entity.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FindShapeQuery {

    private String createdBy;
    private String type;
    private Double areaFrom;
    private Double areaTo;
    private Double perimeterFrom;
    private Double perimeterTo;
    private Double parameterFrom;
    private Double parameterTo;
    private Double parameterFrom2;
    private Double parameterTo2;

}