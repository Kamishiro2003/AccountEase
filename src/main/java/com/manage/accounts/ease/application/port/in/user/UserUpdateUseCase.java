package com.manage.accounts.ease.application.port.in.user;

import com.manage.accounts.ease.domain.model.UserModel;

/**
 * Use case for updating a user.
 */
public interface UserUpdateUseCase {

  /**
   * Updated a user for its username.
   *
   * @param username the name of the user
   * @param user the user to update
   */
  void updateByUsername(String username, UserModel user);
}
