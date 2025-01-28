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
    Page<TaskModel> findByAssignedTo(UUID assignedTo, Pageable pageable);

    @Query("select t from TaskModel t where t.title = :title and t.assignedTo = :user order by t.deadline asc")
    Optional<TaskModel> findByTitleAndAssignedTo(String title, UUID user);

    @Query("SELECT t FROM TaskModel t WHERE t.title = :title AND t.assignedTo = :userId AND t.id <> :currentTaskId")
    Optional<TaskModel> findByTitleAndUserIdExcludingCurrent(@Param("title") String title, @Param("userId") UUID userId, @Param("currentTaskId") UUID currentTaskId);

    @Query("select t from TaskModel t where t.status = :status and t.assignedTo = :user order by t.deadline asc")
    List<TaskModel> findTasksByStatus(@Param("status") TaskStatus status, UUID user);

    @Override
    void deleteById(UUID uuid);
}
