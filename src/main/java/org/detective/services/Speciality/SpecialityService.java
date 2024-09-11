package org.detective.services.Speciality;

import org.detective.entity.DetectiveSpeciality;
import org.detective.entity.Speciality;
import org.detective.repository.DetectiveSpecialityRepository;
import org.detective.repository.SpecialityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecialityService {
    private final SpecialityRepository specialityRepository;

    @Autowired
    private DetectiveSpecialityRepository detectiveSpecialityRepository;

    public SpecialityService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    public List<Speciality> getAllSpecialties() {
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

    public List<Speciality> getSpecialitiesByDetectiveSpecialityIds(List<Long> detectiveSpecialityIds) {
        // DetectiveSpeciality ID 목록을 기준으로 DetectiveSpeciality 엔티티를 조회
        List<DetectiveSpeciality> detectiveSpecialities = detectiveSpecialityRepository.findByIdIn(detectiveSpecialityIds);

        // DetectiveSpeciality 엔티티에서 Speciality ID를 추출
        List<Long> specialityIds = detectiveSpecialities.stream()
                .map(ds -> ds.getSpeciality().getSpecialityId())
                .collect(Collectors.toList());

        // Speciality ID 목록을 기준으로 Speciality를 조회
        return specialityRepository.findBySpecialityIdIn(specialityIds);
    }
}
