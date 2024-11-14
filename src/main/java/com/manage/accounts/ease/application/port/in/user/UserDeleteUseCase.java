package com.manage.accounts.ease.application.port.in.user;

/**
 * Use case for Deleting a user.
 */
public interface UserDeleteUseCase {

  /**
   * Deleting user by its username.
   *
   * @param username the name of the user to delete
   */
  void deleteByUsername(String username);
}
