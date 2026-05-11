package ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos.AgenceDTO;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.dtos.VehicleDTO;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities.Agence;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.entities.Vehicle;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.mappers.RentalMapper;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.repositories.AgenceRepository;
import ma.enset.aarroub.wijdane.aarroub_wijdane_exam_jee.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class RentalServiceImpl implements RentalService {

    private final AgenceRepository agenceRepository;
    private final VehicleRepository vehicleRepository;
    private final RentalMapper rentalMapper;

    @Override
    public AgenceDTO saveAgence(AgenceDTO agenceDTO) {
        log.info("Saving new Agence");
        Agence agence = rentalMapper.fromAgenceDTO(agenceDTO);
        Agence savedAgence = agenceRepository.save(agence);
        return rentalMapper.fromAgence(savedAgence);
    }

    @Override
    public List<AgenceDTO> getAllAgences() {
        return agenceRepository.findAll().stream()
                .map(rentalMapper::fromAgence)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDTO> getVehiclesByAgence(Long agenceId) {
        List<Vehicle> vehicles = vehicleRepository.findByAgenceId(agenceId);
        log.info("Found {} vehicles for agence ID: {}", vehicles.size(), agenceId);

        return vehicles.stream()
                .map(rentalMapper::fromVehicle)
                .collect(Collectors.toList());
    }

    @Override
    public VehicleDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return rentalMapper.fromVehicle(vehicle);
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAll().stream()
                .map(rentalMapper::fromVehicle)
                .collect(Collectors.toList());
    }
}