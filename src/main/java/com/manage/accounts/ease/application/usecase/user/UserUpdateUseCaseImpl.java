package com.manage.accounts.ease.application.usecase.user;

import com.manage.accounts.ease.application.port.in.user.UserUpdateUseCase;
import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.EmailAlreadyExistException;
import com.manage.accounts.ease.domain.exception.UserNotFoundException;
import com.manage.accounts.ease.domain.exception.UsernameAlreadyExistException;
import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.utils.mails.MailManager;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for managing user updating.
 */
@Service
@RequiredArgsConstructor
public class UserUpdateUseCaseImpl implements UserUpdateUseCase {

  private static final String NAME_KEY = "UPDATE";

  private final UserPersistencePort persistencePort;

  private final MailManager manager;

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateByUsername(String username, UserModel user) {

    if (username == null) {
      throw new IllegalArgumentException();
    }
    persistencePort.findByUsername(username).ifPresentOrElse(userToUpdate -> {
      userToUpdate.setUsername(user.getUsername());
      userToUpdate.setPassword(user.getPassword());
      userToUpdate.setEmail(user.getEmail());
      userToUpdate.setEnabled(user.isEnabled());
      userToUpdate.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
      try {
        persistencePort.save(userToUpdate);
        manager.sendAlertMail(username, user.getEmail(), NAME_KEY);
      } catch (DataIntegrityViolationException e) {
        if (e.getMessage().contains("username_unique_constraint")) {
          throw new UsernameAlreadyExistException();
        } else if (e.getMessage().contains("email_unique_constraint")) {
          throw new EmailAlreadyExistException();
        }
        throw e;
      }
    }, () -> {
      throw new UserNotFoundException();
    });
  }
}
