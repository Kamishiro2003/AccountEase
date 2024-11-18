package com.manage.accounts.ease.infrastructure.adapter.out.persistence.repository;

import com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity.UserPersistenceEntity;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for user persistence operations.
 */
public interface UserPersistenceRepository extends JpaRepository<UserPersistenceEntity, String> {

  /**
   * Finds a user by its username.
   *
   * @param username the name of the user
   * @return an {@code Optional} containing the found anime, or empty if not found
   */
  Optional<UserPersistenceEntity> findByUsername(String username);

  /**
   * Calls the stored procedure to retrieve users within a date range.
   *
   * @param startDate the start date of the range
   * @param endDate   the end date of the range
   * @return a list of users created within the specified date range
   */
  @Procedure(name = "get_users_by_date_range")
  List<UserPersistenceEntity> findUsersByDateRange(@Param("start_date") LocalDate startDate,
      @Param("end_date") LocalDate endDate);

  /**
   * Deletes a user by its username.
   *
   * @param username the id of the user to delete
   */
  void deleteByUsername(String username);
}
