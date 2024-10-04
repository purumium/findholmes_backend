package org.detective.repository;

import org.detective.entity.Client;
import org.detective.entity.Detective;
import org.detective.entity.Estimate;
import org.detective.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstimateRepository extends JpaRepository<Estimate, Long> {

    List<Estimate> findByClient(Client client);
    List<Estimate> findByRequest_requestId(Long requestId);
    List<Estimate> findByDetective(Detective detective);

    Estimate findByDetectiveAndRequest_requestId(Detective detective, Long requestId);
}
