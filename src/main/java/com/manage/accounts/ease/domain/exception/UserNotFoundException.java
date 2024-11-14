package com.manage.accounts.ease.domain.exception;

import com.manage.accounts.ease.utils.exception.ExceptionCode;

/**
 * Exception thrown when a requested user cannot be found in the system.
 */
public class UserNotFoundException extends ApplicationException {
  /**
   * Constructs a new {@code UserNotFoundException} with a default error message
   * indicating that the user could not be found.
   */
  public UserNotFoundException() {
    super("User was not found", ExceptionCode.NOT_FOUND, "CLIENT-NOT-FOUND");
  }
}
