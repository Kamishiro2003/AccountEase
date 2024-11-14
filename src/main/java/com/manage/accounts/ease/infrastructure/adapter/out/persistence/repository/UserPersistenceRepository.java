package com.manage.accounts.ease.infrastructure.adapter.out.persistence.repository;

import com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity.UserPersistenceEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

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
   * Deletes a user by its username.
   *
   * @param username the id of the user to delete
   */
  void deleteByUsername(String username);
}
