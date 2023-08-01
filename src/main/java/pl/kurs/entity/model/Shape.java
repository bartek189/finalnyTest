package pl.kurs.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@ToString

public abstract class Shape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String createdBy;
    private LocalDateTime createdAt;
    private int version;
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @JsonBackReference
    private User user;


    public abstract double area();

    public abstract double perimeter();


    public Shape(String type, String createdBy, LocalDateTime createdAt, int version, LocalDateTime lastModifiedAt, String lastModifiedBy, User user) {
        this.type = type;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.version = version;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedBy = lastModifiedBy;
        this.user = user;
    }


    public Shape() {
    }
}
