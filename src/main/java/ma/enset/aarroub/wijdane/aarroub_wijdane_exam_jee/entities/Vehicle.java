package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities;

import jakarta.persistence.*;
import lombok.*;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.enums.VehicleStatus;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 3)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marque;
    private String modele;
    private String matricule;
    private double prixParJour;

    @Temporal(TemporalType.DATE)
    private Date dateMiseEnService;

    @Enumerated(EnumType.STRING)
    private VehicleStatus status;

    @ManyToOne
    private Agence agence;

    @OneToMany(mappedBy = "vehicle")
    private List<Location> locations;
}