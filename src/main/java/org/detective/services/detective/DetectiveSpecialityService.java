package org.detective.services.detective;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.detective.repository.DetectiveSpecialityRepository;

@Service
public class DetectiveSpecialityService {

    @Autowired
    private DetectiveSpecialityRepository detectiveSpecialityRepository;

//    public List<DetectiveSpeciality> getSpecialitiesByDetectiveId(Long detectiveId) {
//        return detectiveSpecialityRepository.findByDetective_Id(detectiveId);
//    }
}