package org.detective.repository;

import org.detective.entity.DeletedStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedStatusRepository extends JpaRepository<DeletedStatus, Long> {
    DeletedStatus findByDeletedId(Long deletedId);
}

