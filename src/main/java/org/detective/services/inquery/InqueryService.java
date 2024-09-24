package org.detective.services.inquery;

import org.detective.entity.*;
import org.detective.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InqueryService {

    private final InqueryReporitory inqueryRepository;

    public InqueryService(InqueryReporitory inqueryReporitory) {
        this.inqueryRepository = inqueryReporitory;
    }

    public Inquery saveInquery(Inquery inquery) {
        return inqueryRepository.save(inquery); // 저장된 엔티티를 반환
    }
}
