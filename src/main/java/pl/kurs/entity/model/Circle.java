package pl.kurs.entity.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Circle extends Shape {

    private double radius;


    public Circle(String type, String createdBy, LocalDateTime createdAt, int version, LocalDateTime lastModifiedAt, String lastModifiedBy, User user, double radius) {
        super(type, createdBy, createdAt, version, lastModifiedAt, lastModifiedBy, user);
        this.radius = radius;
    }


    public void setRadius(double radius) {
        this.radius = radius;
    }
}

