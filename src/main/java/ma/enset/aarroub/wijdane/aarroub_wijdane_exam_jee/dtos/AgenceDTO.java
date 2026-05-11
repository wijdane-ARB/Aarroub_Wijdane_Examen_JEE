package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos;
import lombok.Data;

@Data
public class AgenceDTO {
    private Long id;
    private String nom;
    private String adresse;
    private String ville;
    private String telephone;
}