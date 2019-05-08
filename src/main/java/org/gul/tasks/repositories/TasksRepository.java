package org.gul.tasks.repositories;

import org.gul.tasks.domain.Status;
import org.gul.tasks.domain.Task;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TasksRepository extends CrudRepository<Task, UUID> {
    Optional<Task> findById(UUID id);

    @Modifying
    @Transactional
    @Query("UPDATE Task t SET t.status = :status, t.updated_at = current_timestamp WHERE t.id = :id")
    void updateStatus(@Param("id") UUID id, @Param("status") Status status);
}
