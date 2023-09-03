package pl.kurs.entity.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ShapeResponse {
    private Long id;
    private String type;
    private int version;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime lastModifiedAt;
    private String lastModifiedBy;
    private double area;
    private double perimeter;


    public ShapeResponse(Long id, String type, int version, LocalDateTime createdAt, String createdBy, LocalDateTime lastModifiedAt, String lastModifiedBy) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedBy = lastModifiedBy;
    }

    public ShapeResponse(Long id, String type, int version, LocalDateTime createdAt, String createdBy, LocalDateTime lastModifiedAt, String lastModifiedBy, double area, double perimeter) {
        this.id = id;
        this.type = type;
        this.version = version;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedBy = lastModifiedBy;
        this.area = area;
        this.perimeter = perimeter;
    }
}
