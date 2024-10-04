package org.detective.repository;

import org.detective.entity.Inquery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InqueryReporitory extends JpaRepository<Inquery, Long> {

    // status에 따른 문의 목록 조회
    List<Inquery> findByResponseStatus(Inquery.ResponseStatus status);
}
