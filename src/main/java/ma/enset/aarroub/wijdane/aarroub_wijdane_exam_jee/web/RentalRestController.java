package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.web;

import lombok.AllArgsConstructor;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos.AgenceDTO;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos.VehicleDTO;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.services.RentalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin("*")
public class RentalRestController {

    private RentalService rentalService;

    @GetMapping("/agences")
    public List<AgenceDTO> getAllAgences() {
        return rentalService.getAllAgences();
    }

    @PostMapping("/agences")
    public AgenceDTO saveAgence(@RequestBody AgenceDTO agenceDTO) {
        return rentalService.saveAgence(agenceDTO);
    }

    @GetMapping("/vehicles")
    public List<VehicleDTO> getAllVehicles() {
        return rentalService.getAllVehicles();
    }

    @GetMapping("/agences/{id}/vehicles")
    public List<VehicleDTO> getVehiclesByAgence(@PathVariable Long id) {
        return rentalService.getVehiclesByAgence(id);
    }

    @GetMapping("/vehicles/{id}")
    public VehicleDTO getVehicleById(@PathVariable Long id) {
        return rentalService.getVehicleById(id);
    }

}