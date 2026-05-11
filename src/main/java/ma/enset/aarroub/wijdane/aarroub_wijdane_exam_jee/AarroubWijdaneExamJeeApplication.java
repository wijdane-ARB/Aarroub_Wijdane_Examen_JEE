package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee;

import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities.Agence;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.enums.VehicleStatus;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities.Voiture;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.repositories.AgenceRepository;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.repositories.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AarroubWijdaneExamJeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AarroubWijdaneExamJeeApplication.class, args);
    }

    @Bean
    CommandLineRunner start(VehicleRepository vehicleRepo, AgenceRepository agenceRepo) {
        return args -> {
            Agence agence1 = Agence.builder()
                    .nom("Agency Casa")
                    .ville("Casablanca")
                    .telephone("0522000000")
                    .build();
            agenceRepo.save(agence1);

            Voiture v1 = new Voiture();
            v1.setMarque("Dacia");
            v1.setModele("Logan");
            v1.setPrixParJour(300);
            v1.setNombrePortes(5);
            v1.setTypeCarburant("Diesel");
            v1.setStatus(VehicleStatus.DISPONIBLE);
            v1.setAgence(agence1);


            vehicleRepo.save(v1);

            System.out.println("====== Data Initialized Successfully! ======");
        };
    }
}