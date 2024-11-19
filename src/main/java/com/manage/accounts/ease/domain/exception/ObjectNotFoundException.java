package com.manage.accounts.ease.domain.exception;

import com.manage.accounts.ease.utils.exception.ExceptionCode;

/**
 * Exception thrown when a specific object cannot be found in the system.
 */
public class ObjectNotFoundException extends ApplicationException {
  /**
   * Constructs a new {@code ObjectNotFoundException} with a default error message
   * indicating that the specified object could not be found.
   *
   * @param object the name of the object that was not found
   */
  public ObjectNotFoundException(String object) {
    super(object + " was not found", ExceptionCode.NOT_FOUND, object.toUpperCase() + "-NOT-FOUND");
  }
}
