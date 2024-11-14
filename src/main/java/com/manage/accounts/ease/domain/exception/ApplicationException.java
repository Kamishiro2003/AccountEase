package com.manage.accounts.ease.domain.exception;

import com.manage.accounts.ease.utils.exception.ExceptionCode;
import lombok.Getter;

/**
 * A common exception class for all exceptions in the application.
 */
@Getter
public class ApplicationException extends RuntimeException {

  private final ExceptionCode exceptionCode;

  private final String fullErrorCode;


  /**
   * Constructor for ApplicationException.
   *
   * @param message       the message to be displayed for this exception
   * @param exceptionCode the exception code to be used for this exception
   * @param errorCode     the error code to be used for this exception.
   *                      The error code is a unique identifier for the exception thrown.
   *                      it has the format: identifier, example: USER-NOT-FOUND
   */
  public ApplicationException(String message, ExceptionCode exceptionCode, String errorCode) {

    super(message);
    this.exceptionCode = exceptionCode;
    this.fullErrorCode = errorCode;
  }

  /**
   * Constructor for ApplicationException.
   *
   * @param message       the message to be displayed for this exception
   * @param exceptionCode the exception code to be used for this exception
   * @param errorCode     the error code to be used for this exception.
   *                      The error code is a unique identifier for the exception thrown.
   *                      It has the format: [optional entity prefix]identifier,
   *                      example: USER-NOT-FOUND
   * @param cause         a throwable cause for this exception
   */
  public ApplicationException(String message, ExceptionCode exceptionCode, String errorCode,
      Throwable cause
  ) {

    super(message, cause);
    this.exceptionCode = exceptionCode;
    this.fullErrorCode = errorCode;
  }

  /**
   * Get the exception type from the class name.
   * The exception type is the first three letters of the class name in uppercase.
   */
  protected String getExceptionType() {

    String className = this.getClass().getSimpleName();
    return className.substring(0, 3).toUpperCase();
  }
}
