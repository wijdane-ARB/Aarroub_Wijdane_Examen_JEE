package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee;

import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities.*;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.enums.VehicleStatus;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.repositories.AgenceRepository;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.repositories.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication(exclude = {
        SecurityAutoConfiguration.class,
        UserDetailsServiceAutoConfiguration.class
})
public class AarroubWijdaneExamJeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AarroubWijdaneExamJeeApplication.class, args);
    }
    @Bean
    CommandLineRunner start(AgenceRepository agenceRepository, VehicleRepository vehicleRepository) {
        return args -> {
            Agence agence1 = Agence.builder()
                    .nom("Auto Location Casa")
                    .adresse("Boulevard Zerktouni")
                    .ville("Casablanca")
                    .telephone("0522000000")
                    .build();
            agenceRepository.save(agence1);

            // Création d'une voiture
            Voiture voiture = new Voiture();
            voiture.setMarque("Toyota");
            voiture.setModele("Corolla");
            voiture.setMatricule("12345-A-1");
            voiture.setPrixParJour(350.0);
            voiture.setDateMiseEnService(new Date());
            voiture.setStatus(VehicleStatus.DISPONIBLE);
            voiture.setNombrePortes(5);
            voiture.setTypeCarburant("Hybride");
            voiture.setBoiteVitesse("Automatique");
            voiture.setAgence(agence1);
            vehicleRepository.save(voiture);

            Moto moto = new Moto();
            moto.setMarque("Yamaha");
            moto.setModele("TMAX");
            moto.setMatricule("9876-B-2");
            moto.setPrixParJour(400.0);
            moto.setDateMiseEnService(new Date());
            moto.setStatus(VehicleStatus.DISPONIBLE);
            moto.setCylindree(560);
            moto.setTypeMoto("Scooter");
            moto.setCasqueInclus(true);
            moto.setAgence(agence1);
            vehicleRepository.save(moto);;
        };
    }
}