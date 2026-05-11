package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@DiscriminatorValue("MOT")
@Data @NoArgsConstructor @AllArgsConstructor
public class Moto extends Vehicle {
    private int cylindree;
    private String typeMoto;
    private boolean casqueInclus;
}