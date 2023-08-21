package pl.kurs.entity.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor

public class Circle extends Shape {

    private double radius;
    private double area;
    private double perimeter;


    public Circle(String type, String createdBy, LocalDateTime createdAt, int version, LocalDateTime lastModifiedAt, String lastModifiedBy, User user, double radius) {
        super(type, createdBy, createdAt, version, lastModifiedAt, lastModifiedBy, user);
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        this.area = area();
        this.perimeter = perimeter();
    }
}

