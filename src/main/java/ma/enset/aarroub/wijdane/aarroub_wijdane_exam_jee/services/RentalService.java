package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.services;

import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos.AgenceDTO;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos.VehicleDTO;
import java.util.List;

public interface RentalService {
    AgenceDTO saveAgence(AgenceDTO agenceDTO);
    List<AgenceDTO> getAllAgences();
    List<VehicleDTO> getVehiclesByAgence(Long agenceId);
    VehicleDTO getVehicleById(Long id);
}