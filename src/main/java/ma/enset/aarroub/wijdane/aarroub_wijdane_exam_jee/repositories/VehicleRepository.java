package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.repositories;

import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByAgenceId(Long agenceId);
}