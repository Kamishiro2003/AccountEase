package com.manage.accounts.ease.application.port.in.user;

import com.manage.accounts.ease.domain.model.UserModel;

/**
 * Use case for creating a user.
 */
public interface UserCreateUseCase {

  /**
   * Creates a new user.
   *
   * @param user the user to create
   * @return the created user
   */
  UserModel createByOne(UserModel user);
}
