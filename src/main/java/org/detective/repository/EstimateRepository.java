package org.detective.repository;

import org.detective.entity.Client;
import org.detective.entity.Detective;
import org.detective.entity.Estimate;
import org.detective.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {

    List<Estimate> findByClient(Client client);
    List<Estimate> findByRequest(Request request);
    List<Estimate> findByDetective(Detective detective);
}
