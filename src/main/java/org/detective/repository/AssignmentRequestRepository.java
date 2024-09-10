package org.detective.repository;

import org.detective.entity.AssignmentRequest;
import org.detective.entity.Client;
import org.detective.entity.Detective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRequestRepository extends JpaRepository<AssignmentRequest, Long> {

    List<AssignmentRequest> findByDetective(Detective detective);
}
