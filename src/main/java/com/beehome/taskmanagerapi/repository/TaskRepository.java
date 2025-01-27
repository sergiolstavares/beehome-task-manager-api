package com.beehome.taskmanagerapi.repository;

import com.beehome.taskmanagerapi.model.TaskModel;
import com.beehome.taskmanagerapi.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    Page<TaskModel> findAll(Pageable pageable);

    Page<TaskModel> findByAssignedTo(UUID assignedTo, Pageable pageable);

    Optional<TaskModel> findByTitle(String title);

    @Query("select t from TaskModel t where t.status = :status and t.assignedTo = :user order by t.deadline asc")
    List<TaskModel> findTasksByStatus(@Param("status") TaskStatus status, UUID user);

    Optional<TaskModel> findById(UUID uuid);

    TaskModel save(TaskModel task);

    @Override
    void deleteById(UUID uuid);
}
