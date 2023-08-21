package pl.kurs.entity.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString

public class Square extends Shape {
    private double side;
    private double area;
    private double perimeter;


    public Square(String type, String createdBy, LocalDateTime createdAt, int version, LocalDateTime lastModifiedAt, String lastModifiedBy, User user, double side) {
        super(type, createdBy, createdAt, version, lastModifiedAt, lastModifiedBy, user);
        this.side = side;
    }

    @Override
    public double area() {
        return side * side;
    }

    @Override
    public double perimeter() {
        return 4 * side;
    }

    public void setSide(double newSide) {
        this.side = newSide;
        this.area = area();
        this.perimeter = perimeter();
    }
}
