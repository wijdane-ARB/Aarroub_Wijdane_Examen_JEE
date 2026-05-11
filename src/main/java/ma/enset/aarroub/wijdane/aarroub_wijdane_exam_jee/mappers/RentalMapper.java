package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.mappers;

import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos.AgenceDTO;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos.VehicleDTO;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities.Agence;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities.Moto;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities.Vehicle;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities.Voiture;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class RentalMapper {

    public AgenceDTO fromAgence(Agence agence) {
        AgenceDTO agenceDTO = new AgenceDTO();
        BeanUtils.copyProperties(agence, agenceDTO);
        return agenceDTO;
    }

    public Agence fromAgenceDTO(AgenceDTO agenceDTO) {
        Agence agence = new Agence();
        BeanUtils.copyProperties(agenceDTO, agence);
        return agence;
    }

    public VehicleDTO fromVehicle(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        BeanUtils.copyProperties(vehicle, vehicleDTO);
        if (vehicle instanceof Voiture) {
            vehicleDTO.setType("Voiture");
        } else if (vehicle instanceof Moto) {
            vehicleDTO.setType("Moto");
        }
        if (vehicle.getAgence() != null) {
            vehicleDTO.setAgenceId(vehicle.getAgence().getId());
        }
        return vehicleDTO;
    }
}