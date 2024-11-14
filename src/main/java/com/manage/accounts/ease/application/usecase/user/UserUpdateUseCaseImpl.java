package com.manage.accounts.ease.application.usecase.user;

import com.manage.accounts.ease.application.port.in.user.UserUpdateUseCase;
import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.EmailAlreadyExistException;
import com.manage.accounts.ease.domain.exception.UserNotFoundException;
import com.manage.accounts.ease.domain.exception.UsernameAlreadyExistException;
import com.manage.accounts.ease.domain.model.UserModel;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for managing user updating.
 */
@Service
@RequiredArgsConstructor
public class UserUpdateUseCaseImpl implements UserUpdateUseCase {

  private final UserPersistencePort persistencePort;

  private final JavaMailSender mailSender;

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
        sendAccountUpdatedEmail(user);
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

  private void sendAccountUpdatedEmail(UserModel user) {
    String subject = "Account Updated Successfully on AccountEase";
    String emailBody = String.format("""
        Dear %s,
        
        We wanted to inform you that your account details on AccountEase
        have been successfully updated.
        If you did not make this change or have any questions,
        please contact our support team immediately.
        
        Thank you for trusting AccountEase to manage your account!
        
        Best regards,
        The AccountEase Team
        """, user.getUsername());
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(user.getEmail());
    message.setSubject(subject);
    message.setText(emailBody);
    mailSender.send(message);
  }
}
