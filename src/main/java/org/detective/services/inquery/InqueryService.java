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

    // 문의글 저장
    public Inquery saveInquery(Inquery inquery) {
        return inqueryRepository.save(inquery); // 저장된 엔티티를 반환
    }

    // 전체 문의 조회
    public List<Inquery> getAllInqueries() {
        return inqueryRepository.findAll();
    }

    // 문의글 상태에 따라 불러오기
    public List<Inquery> getInqueriesByStatus(Inquery.ResponseStatus status) {
        return inqueryRepository.findByResponseStatus(status);
    }

    // 문의글 상세 보기
    public Inquery getInqueryById(Long id) {
        return inqueryRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("해당 문의글을 찾을 수 없습니다"));
    }

    // 문의글 상태 업데이트
    public void updateInqueryStatsus(Long id) {
        // 문의글 id로 정보 가지고 오기
        Inquery inquery = inqueryRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("해당 문의글을 찾을 수 없습니다"));

        // 해당 문의글의 상태를 update
        inquery.setResponseStatus(Inquery.ResponseStatus.COMPLETE);
        inqueryRepository.save(inquery);
    }
}
