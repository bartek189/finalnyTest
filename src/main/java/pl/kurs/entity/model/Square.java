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
public class Square extends Shape {
    private double side;


    public Square(String type, String createdBy, LocalDateTime createdAt, int version, LocalDateTime lastModifiedAt, String lastModifiedBy, User user, double side) {
        super(type, createdBy, createdAt, version, lastModifiedAt, lastModifiedBy, user);
        this.side = side;
    }

    public void setSide(double newSide) {
        this.side = newSide;

    }
}
