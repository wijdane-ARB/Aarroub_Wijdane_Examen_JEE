// File: src/main/java/ma/enset/aarroub/wijdane/aarroub_wijdane_exam_jee/entities/Agence.java
package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Agence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private String ville;
    private String telephone;

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Vehicle> vehicles;
}