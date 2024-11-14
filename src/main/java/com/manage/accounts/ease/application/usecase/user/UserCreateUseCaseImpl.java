package com.manage.accounts.ease.application.usecase.user;

import com.manage.accounts.ease.application.port.in.user.UserCreateUseCase;
import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.EmailAlreadyExistException;
import com.manage.accounts.ease.domain.exception.UsernameAlreadyExistException;
import com.manage.accounts.ease.domain.model.UserModel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for managing user creation.
 */
@Service
@RequiredArgsConstructor
public class UserCreateUseCaseImpl implements UserCreateUseCase {

  private final UserPersistencePort persistencePort;

  private final PasswordEncoder passwordEncoder;

  private final JavaMailSender mailSender;

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
      sendWelcomeEmail(user);
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

  /**
   * Sends a welcome email to the newly created user.
   *
   * @param user the newly created user
   */
  private void sendWelcomeEmail(UserModel user) {
    String subject = "Welcome to AccountEase, " + user.getUsername() + "!";
    String emailBody = String.format("""
        Dear %s,
        
        We are thrilled to welcome you to AccountEase! Thank you for creating an account with us.
        
        Here are your account details:
        - Username: %s
        - Role: %s
        
        If you have any questions or need assistance, feel free to reach out to our support team.
        
        Welcome aboard,
        The AccountEase Team
        """, user.getUsername(), user.getUsername(), user.getRole().toString());
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(user.getEmail());
    message.setSubject(subject);
    message.setText(emailBody);
    mailSender.send(message);
  }

}
