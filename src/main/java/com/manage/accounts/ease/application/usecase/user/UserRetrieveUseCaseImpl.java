package com.manage.accounts.ease.application.usecase.user;

import com.manage.accounts.ease.application.port.in.user.UserRetrieveUseCase;
import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.UserNotFoundException;
import com.manage.accounts.ease.domain.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for managing user retrieving.
 */
@Service
@RequiredArgsConstructor
public class UserRetrieveUseCaseImpl implements UserRetrieveUseCase {

  private final UserPersistencePort persistencePort;

  /**
   * {@inheritDoc}
   */
  @Override
  public UserModel findByUsername(String username) {
    if (username == null) {
      throw new IllegalArgumentException();
    }
    return persistencePort.findByUsername(username).orElseThrow(UserNotFoundException::new);
  }
}
