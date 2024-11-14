package com.manage.accounts.ease.domain.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the structure of an API error response with code, message, path, and optional details.
 */
@Getter
@Setter
public class ApiErrorModel {

  private final String code;

  private final String message;

  private final String path;

  @JsonInclude(JsonInclude.Include.NON_NULL) // Only include if not null
  private List<String> details;

  @JsonInclude(JsonInclude.Include.NON_NULL) // Only include if not null
  private String debugMessage;

  private LocalDateTime timestamp;

  /**
   * Constructs an {@code ApiError} with the specified code, message, and path.
   *
   * @param code the unique error code
   * @param message the error message description
   * @param path the URI where the error occurred
   */
  public ApiErrorModel(String code, String message, String path) {
    this.code = code;
    this.message = message;
    this.path = path;
    this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
  }

  /**
   * Constructs an {@code ApiError} with the specified code, message, path, and additional details.
   *
   * @param code the unique error code
   * @param message the error message description
   * @param path the URI where the error occurred
   * @param details additional error details
   */
  public ApiErrorModel(String code, String message, String path, List<String> details) {
    this.code = code;
    this.message = message;
    this.details = details;
    this.path = path;
    this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
  }

  /**
   * Constructs an {@code ApiError} with the specified code, message, path and exception.
   *
   * @param code the unique error code
   * @param message the error message description
   * @param path the URI where the error occurred
   */
  public ApiErrorModel(String code, String message, String path, Throwable ex) {
    this.code = code;
    this.message = message;
    this.path = path;
    this.debugMessage = ex.getClass().getSimpleName();
    this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
  }
}
