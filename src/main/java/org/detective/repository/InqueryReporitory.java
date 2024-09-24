package org.detective.repository;

import org.detective.entity.Inquery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InqueryReporitory extends JpaRepository<Inquery, Long> {

}
