package org.detective.services.detective;

import org.detective.entity.Speciality;
import org.detective.repository.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyService {

    @Autowired
    private SpecialityRepository specialityRepository;

    public List<Speciality> getAllSpecialties() {
        // 전체 Specialty 리스트를 조회합니다.
        return specialityRepository.findAll();
    }

    public Speciality getSpecialityById(Long id) {
        Optional<Speciality> specialityOpt = specialityRepository.findById(id);
        if (specialityOpt.isPresent()) {
            return specialityOpt.get();
        } else {
            // Speciality가 존재하지 않을 때의 처리
            throw new RuntimeException("Speciality with ID " + id + " not found.");
        }
    }
}
