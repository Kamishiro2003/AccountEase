package com.manage.accounts.ease.domain.exception;

import com.manage.accounts.ease.utils.exception.ExceptionCode;

/**
 * Thrown when an invalid token is provided.
 * Sets a default message, exception code, and error identifier.
 */
public class InvalidTokenException extends ApplicationException {

  /**
   * Constructs the InvalidTokenException with a default message, exception code, and identifier.
   */
  public InvalidTokenException() {
    super("Token is invalid", ExceptionCode.UNAUTHORIZED,
        "TOKEN-INVALID"
    );
  }
}
