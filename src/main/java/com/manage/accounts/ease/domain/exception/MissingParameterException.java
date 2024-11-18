package com.manage.accounts.ease.domain.exception;

import com.manage.accounts.ease.utils.exception.ExceptionCode;
import java.util.List;

/**
 * Exception thrown when required parameters are missing.
 */
public class MissingParameterException extends ApplicationException {

  /**
   * Constructs a new exception for a single missing parameter.
   *
   * @param parameter the name of the missing parameter
   */
  public MissingParameterException(String parameter) {
    super("The parameter: " + parameter + " is missing.", ExceptionCode.INVALID_INPUT,
        "MISSING-PARAMETER"
    );
  }

  /**
   * Constructs a new exception for multiple missing parameters.
   *
   * @param parameters the list of missing parameters
   */
  public MissingParameterException(List<String> parameters) {
    super("The parameters: " + parameters + " are missing.", ExceptionCode.INVALID_INPUT,
        "MISSING-PARAMETERS"
    );
  }
}
