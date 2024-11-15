package com.manage.accounts.ease.domain.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.manage.accounts.ease.utils.exception.ExceptionCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationExceptionTest {

  @Test
  @DisplayName("Constructor without cause - Should set message, exceptionCode, and errorCode")
  void constructorWithoutCause_ShouldInitializeFields() {
    // Arrange
    String message = "Test exception message";
    ExceptionCode exceptionCode = ExceptionCode.NOT_FOUND;
    String errorCode = "USER-NOT-FOUND";

    // Act
    ApplicationException exception = new ApplicationException(message, exceptionCode, errorCode);

    // Assert
    assertEquals(message, exception.getMessage());
    assertEquals(exceptionCode, exception.getExceptionCode());
    assertEquals(errorCode, exception.getFullErrorCode());
    assertNull(exception.getCause());
  }

  @Test
  @DisplayName("Constructor with cause - Should set message, exceptionCode, errorCode, and cause")
  void constructorWithCause_ShouldInitializeFieldsIncludingCause() {
    // Arrange
    String message = "Test exception with cause";
    ExceptionCode exceptionCode = ExceptionCode.INVALID_INPUT;
    String errorCode = "INPUT-INVALID";
    Throwable cause = new RuntimeException("Root cause");

    // Act
    ApplicationException exception =
        new ApplicationException(message, exceptionCode, errorCode, cause);

    // Assert
    assertEquals(message, exception.getMessage());
    assertEquals(exceptionCode, exception.getExceptionCode());
    assertEquals(errorCode, exception.getFullErrorCode());
    assertEquals(cause, exception.getCause());
  }

  @Test
  @DisplayName("getExceptionType - Should return the first three letters of the class name in uppercase")
  void getExceptionType_ShouldReturnClassAbbreviation() {
    // Arrange
    ApplicationException exception =
        new ApplicationException("Test message", ExceptionCode.NOT_FOUND, "USER-NOT-FOUND");

    // Act
    String exceptionType = exception.getExceptionType();

    // Assert
    assertEquals("APP", exceptionType);
  }
}
