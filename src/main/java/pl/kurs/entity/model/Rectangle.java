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
public class Rectangle extends Shape {
    private double width;
    private double height;
    @JsonCreator
    public Rectangle(@JsonProperty("type")String type,@JsonProperty("createdAt") LocalDateTime createdAt,@JsonProperty("version") int version
            , @JsonProperty("createdBy")String createdBy, @JsonProperty("lastModifiedAt")LocalDateTime lastModifiedAt,@JsonProperty("lastModifiedBy") String lastModifiedBy
            , @JsonProperty("area")double area
            , @JsonProperty("perimeter")double perimeter, @JsonProperty("width") double width, @JsonProperty("height")double height) {
        super(type, createdAt, version, createdBy, lastModifiedAt, lastModifiedBy, area, perimeter);
        this.width = width;
        this.height = height;
    }
}
