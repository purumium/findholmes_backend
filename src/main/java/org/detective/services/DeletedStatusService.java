package org.detective.services;

import jakarta.persistence.EntityNotFoundException;
import org.detective.entity.DeletedStatus;
import org.detective.repository.DeletedStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletedStatusService {

    @Autowired
    private DeletedStatusRepository deletedStatusRepository;

    @Transactional
    public void incrementReasonByIndex(Long deletedId, int index, int role) {
        DeletedStatus status = deletedStatusRepository.findByDeletedId(deletedId);

        if (status != null) {
            if(role==0){
                switch (index) {
                    case 0: status.setReason0(status.getReason0() + 1); break;
                    case 1: status.setReason1(status.getReason1() + 1); break;
                    case 2: status.setReason2(status.getReason2() + 1); break;
                    case 3: status.setReason3(status.getReason3() + 1); break;
                    case 4: status.setReason4(status.getReason4() + 1); break;
                    case 5: status.setReason5(status.getReason5() + 1); break;
                    default: throw new IllegalArgumentException("Invalid index: " + index);
                }
            }else if(role==1){
                switch (index) {
                    case 0: status.setDeReason0(status.getDeReason0() + 1); break;
                    case 1: status.setDeReason1(status.getDeReason1() + 1); break;
                    case 2: status.setDeReason2(status.getDeReason2() + 1); break;
                    case 3: status.setDeReason3(status.getDeReason3() + 1); break;
                    case 4: status.setDeReason4(status.getDeReason4() + 1); break;
                    case 5: status.setDeReason5(status.getDeReason5() + 1); break;
                    default: throw new IllegalArgumentException("Invalid index: " + index);
                }
            }

            deletedStatusRepository.save(status); // 변경된 엔티티 저장
        } else {
            throw new EntityNotFoundException("DeletedStatus not found for id: " + deletedId);
        }
    }
}
