package org.detective.repository;

import org.detective.dto.InqueryDTO;
import org.detective.entity.Inquery;
import org.detective.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InqueryReporitory extends JpaRepository<Inquery, Long> {

    // status에 따른 문의 목록 조회
    List<Inquery> findByResponseStatus(Inquery.ResponseStatus status);

    // User 정보를 기반으로 문의글 리스트 가져오기
    List<Inquery> findByUser(User user);
}
