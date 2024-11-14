package com.manage.accounts.ease.infrastructure.adapter.in.rest.controller.application;

import static com.manage.accounts.ease.utils.exception.ErrorCatalog.USER_INVALID_INPUTS;

import com.manage.accounts.ease.domain.exception.ApplicationException;
import com.manage.accounts.ease.domain.model.ApiErrorModel;
import com.manage.accounts.ease.utils.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for the application.
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

  /**
   * Handles {@link ApplicationException} by building a standardized {@link ApiErrorModel} response.
   *
   * @param req the HTTP request that triggered the exception
   * @param ex  the application-specific exception with error details
   * @return a {@link ResponseEntity} with the error information and appropriate HTTP status
   */
  @ExceptionHandler(value = ApplicationException.class)
  public ResponseEntity<ApiErrorModel> handleApplicationException(HttpServletRequest req,
      ApplicationException ex
  ) {

    HttpStatus statusCode = ErrorCode.getHttpStatusFromExceptionCode(ex.getExceptionCode());
    String message = ex.getMessage();
    String path = req.getRequestURI();
    String errorCode = ex.getFullErrorCode();

    ApiErrorModel apiErrorModel = new ApiErrorModel(errorCode, message, path);

    log.debug("an application exception occurred with code: {} and message: {}",
        ex.getFullErrorCode(), ex.getMessage()
    );
    return ResponseEntity.status(statusCode).body(apiErrorModel);
  }

  /**
   * Handles {@link MethodArgumentNotValidException} for invalid method arguments in request bodies.
   * Builds an {@link ApiErrorModel} response with validation error details.
   *
   * @param req the HTTP request where the exception occurred
   * @param ex  the exception containing validation errors
   * @return a {@link ResponseEntity} with {@link ApiErrorModel} details
   */
  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorModel> handlerMethodArgumentNotValidException(
      HttpServletRequest req, MethodArgumentNotValidException ex
  ) {

    HttpStatus statusCode = ErrorCode.getHttpStatusFromExceptionCode(USER_INVALID_INPUTS.getCode());
    String path = req.getRequestURI();
    String message = USER_INVALID_INPUTS.getMessage();
    String errorCode = USER_INVALID_INPUTS.getErrCode();
    BindingResult result = ex.getBindingResult();
    List<String> details = result.getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();
    ApiErrorModel apiErrorModel = new ApiErrorModel(errorCode, message, path, details);
    return ResponseEntity.status(statusCode).body(apiErrorModel);
  }

  /**
   * Handles generic exceptions and returns a 500 response.
   *
   * @param exception the exception
   * @return the error response with internal server error status
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorModel> handlerGenericError(HttpServletRequest req,
      Exception exception
  ) {

    String path = req.getRequestURI();

    ApiErrorModel apiErrorModel =
        new ApiErrorModel("E-UNKNOWN-ERROR", exception.getMessage(), path, exception);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorModel);
  }

}
