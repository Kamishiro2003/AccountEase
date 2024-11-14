package com.manage.accounts.ease.application.usecase.user;

import com.manage.accounts.ease.application.port.in.user.UserDeleteUseCase;
import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.UserNotFoundException;
import com.manage.accounts.ease.domain.model.UserModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for managing role deleting.
 */
@Service
@RequiredArgsConstructor
public class UserDeleteUseCaseImpl implements UserDeleteUseCase {

  private final UserPersistencePort persistencePort;

  private final JavaMailSender mailSender;

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
      sendAccountDeletionEmail(user);
    }, () -> {
      throw new UserNotFoundException();
    });
  }

  private void sendAccountDeletionEmail(UserModel user) {
    String subject = "Account Deletion Notice from AccountEase";
    String emailBody = String.format("""
        Dear %s,
        
        We wanted to let you know that your account with AccountEase has been successfully deleted.
        If this was a mistake or if you have any questions, please contact our support team.
        
        We're here to assist you at any time.
        
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
