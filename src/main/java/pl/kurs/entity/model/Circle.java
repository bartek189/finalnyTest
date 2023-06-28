package pl.kurs.entity.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class Circle extends Shape {

    private double radius;

    @JsonCreator
    public Circle(@JsonProperty("type")String type,@JsonProperty("createdAt") LocalDateTime createdAt,@JsonProperty("version") int version
            , @JsonProperty("createdBy")String createdBy, @JsonProperty("lastModifiedAt")LocalDateTime lastModifiedAt,@JsonProperty("lastModifiedBy") String lastModifiedBy
            , @JsonProperty("area")double area
            , @JsonProperty("perimeter")double perimeter, @JsonProperty("radius")double radius) {
        super(type, createdAt, version, createdBy, lastModifiedAt, lastModifiedBy, area, perimeter);
        this.radius = radius;
    }
}

