package org.detective.repository;

import org.detective.entity.Client;
import org.detective.entity.Request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository  extends JpaRepository<Request, Long> {
    Request findByRequestId(Long requestId);
    List<Request> findByClient(Client client);
}
