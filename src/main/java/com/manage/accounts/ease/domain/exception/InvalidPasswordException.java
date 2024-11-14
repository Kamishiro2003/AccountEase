package com.manage.accounts.ease.domain.exception;

import com.manage.accounts.ease.utils.exception.ExceptionCode;

public class InvalidPasswordException extends ApplicationException {

  public InvalidPasswordException() {
    super("password provided is incorrect", ExceptionCode.INVALID_PASSWORD, "INVALID-PASSWORD");
  }
}
