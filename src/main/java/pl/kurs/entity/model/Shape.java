package pl.kurs.entity.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Shape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private LocalDateTime createdAt;
    private int version;
    private String createdBy;
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;
    private double area;
    private double perimeter;


    public Shape(String type, LocalDateTime createdAt, int version, String createdBy, LocalDateTime lastModifiedAt, String lastModifiedBy, double area, double perimeter) {
        this.type = type;
        this.createdAt = createdAt;
        this.version = version;
        this.createdBy = createdBy;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedBy = lastModifiedBy;
        this.area = area;
        this.perimeter = perimeter;
    }

    public Shape() {
    }
}
