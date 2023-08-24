package pl.kurs.entity.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Rectangle extends Shape {

    private double width;
    private double height;

    public Rectangle(String type, String createdBy, LocalDateTime createdAt, int version, LocalDateTime lastModifiedAt, String lastModifiedBy, User user, double width, double height) {
        super(type, createdBy, createdAt, version, lastModifiedAt, lastModifiedBy, user);
        this.width = width;
        this.height = height;
    }
}







