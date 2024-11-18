package com.manage.accounts.ease.application.port.out;

import com.manage.accounts.ease.domain.model.UserModel;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Persistence operations for user data.
 */
public interface UserPersistencePort {

  /**
   * Finds a user by its username.
   *
   * @param username the name of the user
   * @return an {@code Optional} containing the found user, or empty if not found
   */
  Optional<UserModel> findByUsername(String username);

  /**
   * Retrieves all user entries.
   *
   * @return a list of all user
   */
  List<UserModel> findAll();

  /**
   * Retrieves users created within a specific date range.
   *
   * @param startDate the start date of the range (inclusive)
   * @param endDate   the end date of the range (inclusive)
   * @return a list of users created within the specified range
   */
  List<UserModel> findUsersByDateRange(LocalDate startDate, LocalDate endDate);

  /**
   * Saves a new or existing user.
   *
   * @param user the user to save
   * @return the saved user
   */
  UserModel save(UserModel user);

  /**
   * Deletes a user by its username.
   *
   * @param username the id of the user to delete
   */
  void deleteOne(String username);
}
