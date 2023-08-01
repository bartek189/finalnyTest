package pl.kurs.entity.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Rectangle extends Shape {

    private double width;
    private double height;
    private double area;
    private double perimeter;

    public Rectangle(String type, String createdBy, LocalDateTime createdAt, int version, LocalDateTime lastModifiedAt, String lastModifiedBy, User user, double width, double height) {
        super(type, createdBy, createdAt, version, lastModifiedAt, lastModifiedBy, user);
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return height * width;
    }

    @Override
    public double perimeter() {
        return 2 * (width + width);
    }

    public void setDimensions(double newHeight, double newWidth) {
        this.width = newWidth;
        this.height = newHeight;
        this.area = area();
        this.perimeter = perimeter();
    }
}







