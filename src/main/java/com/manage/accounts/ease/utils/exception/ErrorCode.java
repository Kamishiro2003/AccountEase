package com.manage.accounts.ease.utils.exception;

import org.springframework.http.HttpStatus;

/**
 * This class maps general exception codes to HTTP status codes.
 */
public class ErrorCode {

  /**
   * Maps an {@link ExceptionCode} to an appropriate {@link HttpStatus} response code.
   * This method uses a `switch` expression to handle various types of exception codes,
   * mapping them to specific HTTP status codes that align with RESTFUL best practices.
   *
   * @param exceptionCode the {@link ExceptionCode} representing the specific error encountered
   * @return the corresponding {@link HttpStatus} to use in the HTTP response
   */
  public static HttpStatus getHttpStatusFromExceptionCode(ExceptionCode exceptionCode) {

    return switch (exceptionCode) {
      case INVALID_INPUT, INVALID_ID, ID_NOT_PROVIDED, INVALID_PASSWORD -> HttpStatus.BAD_REQUEST;
      case NOT_FOUND -> HttpStatus.NOT_FOUND;
      case DUPLICATED_RECORD, INVALID_OPERATION -> HttpStatus.CONFLICT;
      case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
      case FORBIDDEN -> HttpStatus.FORBIDDEN;
      default -> HttpStatus.INTERNAL_SERVER_ERROR;
    };
  }
}
