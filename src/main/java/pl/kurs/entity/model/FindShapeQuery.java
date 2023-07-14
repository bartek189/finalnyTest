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
    private String parameterName;
    private Double valueFrom;
    private Double valueTo;

}