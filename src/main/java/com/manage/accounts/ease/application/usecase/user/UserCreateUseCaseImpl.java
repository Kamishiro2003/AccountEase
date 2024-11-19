package com.manage.accounts.ease.application.usecase.user;

import com.manage.accounts.ease.application.port.in.user.UserCreateUseCase;
import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.EmailAlreadyExistException;
import com.manage.accounts.ease.domain.exception.UsernameAlreadyExistException;
import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.utils.mails.MailManager;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for managing user creation.
 */
@Service
@RequiredArgsConstructor
public class UserCreateUseCaseImpl implements UserCreateUseCase {

  private static final String NAME_KEY = "CREATE";

  private final UserPersistencePort persistencePort;

  private final MailManager manager;

  private final PasswordEncoder passwordEncoder;


  /**
   * {@inheritDoc}
   */
  @Override
  public UserModel createByOne(UserModel user) {

    user.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC));
    user.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
    user.setEnabled(true);
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    try {
      UserModel userCreated = persistencePort.save(user);
      manager.sendWelcomeEmail(userCreated.getUsername(), userCreated.getEmail(),
          userCreated.getRole().toString(), NAME_KEY
      );
      return userCreated;
    } catch (DataIntegrityViolationException e) {
      if (e.getMessage().contains("username_unique_constraint")) {
        throw new UsernameAlreadyExistException();
      } else if (e.getMessage().contains("email_unique_constraint")) {
        throw new EmailAlreadyExistException();
      }
      throw e;
    }
  }
}
