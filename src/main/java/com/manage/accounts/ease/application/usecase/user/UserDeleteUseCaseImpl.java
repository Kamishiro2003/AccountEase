package com.manage.accounts.ease.application.usecase.user;

import com.manage.accounts.ease.application.port.in.user.UserDeleteUseCase;
import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.UserNotFoundException;
import com.manage.accounts.ease.utils.mails.MailManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for managing role deleting.
 */
@Service
@RequiredArgsConstructor
public class UserDeleteUseCaseImpl implements UserDeleteUseCase {

  private static final String NAME_KEY = "DELETE";

  private final UserPersistencePort persistencePort;

  private final MailManager manager;

  /**
   * {@inheritDoc}
   */
  @Transactional
  @Override
  public void deleteByUsername(String username) {

    if (username == null) {
      throw new IllegalArgumentException();
    }
    persistencePort.findByUsername(username).ifPresentOrElse(user -> {
      persistencePort.deleteOne(username);
      manager.sendAlertMail(username, user.getEmail(), NAME_KEY);
    }, () -> {
      throw new UserNotFoundException();
    });
  }
}
