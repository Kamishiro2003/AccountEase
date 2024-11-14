package com.manage.accounts.ease.domain.exception;

import com.manage.accounts.ease.utils.exception.ExceptionCode;

public class InvalidTokenException extends ApplicationException {

  public InvalidTokenException() {
    super("Token is invalid", ExceptionCode.UNAUTHORIZED,
        "TOKEN-INVALID"
    );
  }
}
