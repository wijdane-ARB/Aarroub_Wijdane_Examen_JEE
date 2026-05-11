package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos;
import lombok.Data;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.enums.VehicleStatus;
import java.util.Date;

@Data
public class VehicleDTO {
    private Long id;
    private String marque;
    private String modele;
    private String matricule;
    private double prixParJour;
    private Date dateMiseEnService;
    private VehicleStatus status;
    private String type;
    private Long agenceId;
}