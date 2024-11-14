package com.manage.accounts.ease.application.port.in.user;

import com.manage.accounts.ease.domain.model.UserModel;

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
}
