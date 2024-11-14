package com.manage.accounts.ease.domain.exception;

import com.manage.accounts.ease.utils.exception.ExceptionCode;

/**
 * Exception thrown when an attempt to create or update a user account fails
 * because the provided username is already associated with an existing user.
 */
public class UsernameAlreadyExistException extends ApplicationException {
  /**
   * Constructs a new {@code UsernameAlreadyExistException} with a default error message
   * indicating that the username is already assigned to a user account.
   */
  public UsernameAlreadyExistException() {
    super("Username must be unique", ExceptionCode.DUPLICATED_RECORD, "USER-UNIQUE-NAME");
  }
}
