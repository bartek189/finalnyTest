package pl.kurs.entity.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Square extends Shape {

    private double side;

    @JsonCreator
    public Square(@JsonProperty("type")String type,@JsonProperty("createdAt") LocalDateTime createdAt,@JsonProperty("version") int version
            , @JsonProperty("createdBy")String createdBy, @JsonProperty("lastModifiedAt")LocalDateTime lastModifiedAt,@JsonProperty("lastModifiedBy") String lastModifiedBy
            , @JsonProperty("area")double area
            , @JsonProperty("perimeter")double perimeter, @JsonProperty("side")double side) {
        super(type, createdAt, version, createdBy, lastModifiedAt, lastModifiedBy, area, perimeter);
        this.side = side;
    }

    public Square(String type, LocalDateTime createdAt, int version, String createdBy, LocalDateTime lastModifiedAt, String lastModifiedBy, double area, double perimeter) {
        super(type, createdAt, version, createdBy, lastModifiedAt, lastModifiedBy, area, perimeter);
    }
}