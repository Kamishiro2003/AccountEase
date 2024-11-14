package com.manage.accounts.ease.utils.exception;

import lombok.Getter;

/**
 * Catalog of error codes and messages for the application.
 */
@Getter
public enum ErrorCatalog {

  /**
   * Error for invalid anime parameters.
   */
  USER_INVALID_INPUTS(ExceptionCode.INVALID_INPUT, "USER-INVALID-PARAMETERS",
      "Invalid user parameters in the request"
  );

  private final ExceptionCode code;

  private final String message;

  private final String errCode;

  /**
   * Constructs an error catalog entry with a code and message.
   *
   * @param code    the unique error code
   * @param errCode the name of the error code
   * @param message the error message
   */
  ErrorCatalog(ExceptionCode code, String errCode, String message) {

    this.code = code;
    this.errCode = errCode;
    this.message = message;
  }
}
