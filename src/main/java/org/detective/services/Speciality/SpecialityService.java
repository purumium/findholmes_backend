package org.detective.services.Speciality;

import org.detective.entity.Speciality;
import org.detective.repository.SpecialityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialityService {
    private final SpecialityRepository specialityRepository;

    public SpecialityService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    public List<Speciality> getAllSpecialties() {
        return specialityRepository.findAll();
    }
}
