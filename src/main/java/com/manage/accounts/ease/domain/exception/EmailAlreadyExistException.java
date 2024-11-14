package com.manage.accounts.ease.domain.exception;

import com.manage.accounts.ease.utils.exception.ExceptionCode;

/**
 * Exception thrown when an attempt to create or update a user account fails
 * because the provided email is already associated with an existing user.
 */
public class EmailAlreadyExistException extends ApplicationException {
  /**
   * Constructs a new {@code EmailAlreadyExistException} with a default error message
   * indicating that the email is already assigned to a user account.
   */
  public EmailAlreadyExistException() {
    super("Email user must be unique", ExceptionCode.DUPLICATED_RECORD,
        "USER-UNIQUE-EMAIL"
    );
  }
}
