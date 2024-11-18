package com.manage.accounts.ease.application.port.in.user;

import com.manage.accounts.ease.domain.model.UserModel;
import java.time.LocalDate;
import java.util.List;

/**
 * Use case for retrieving user data.
 */
public interface UserRetrieveUseCase {

  /**
   * Finds user by its name.
   *
   * @param username the name of the user.
   * @return the user with the specific name.
   */
  UserModel findByUsername(String username);

  /**
   * Retrieves users created within a specific date range.
   *
   * @param startDate the start date of the range (inclusive)
   * @param endDate   the end date of the range (inclusive)
   * @return a list of users created within the specified range
   */
  List<UserModel> findUsersByDateRange(LocalDate startDate, LocalDate endDate);

}
