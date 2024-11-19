package com.manage.accounts.ease.utils.mails;

import com.manage.accounts.ease.application.usecase.mail.message.MailMessageRetrieveUseCaseImpl;
import com.manage.accounts.ease.domain.model.MailMessageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Component responsible for managing and sending emails.
 */
@Component
@RequiredArgsConstructor
public class MailManager {

  private final MailMessageRetrieveUseCaseImpl retrieveUseCase;

  private final JavaMailSender mailSender;

  /**
   * Sends a welcome email to the specified recipient.
   *
   * @param username the username of the recipient
   * @param email the email address of the recipient
   * @param role the role of the recipient
   * @param nameKey the key used to retrieve the mail template
   */
  public void sendWelcomeEmail(String username, String email, String role, String nameKey) {
    MailMessageModel model = retrieveUseCase.findByNameKey(nameKey);
    String subject = String.format(model.getSubject(), username, "!");
    String body = String.format(model.getBody(), username, username, role);
    this.sendMail(email, subject, body);
  }

  /**
   * Sends an alert email to the specified recipient.
   *
   * @param username the username associated with the alert
   * @param email the email address of the recipient
   * @param nameKey the key used to retrieve the mail template
   */
  public void sendAlertMail(String username, String email, String nameKey) {
    MailMessageModel model = retrieveUseCase.findByNameKey(nameKey);
    String body = String.format(model.getBody(), username);
    this.sendMail(email, model.getSubject(), body);
  }

  /**
   * Sends an email with the specified details.
   *
   * @param email the recipient's email address
   * @param subject the subject of the email
   * @param body the body content of the email
   */
  private void sendMail(String email, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(email);
    message.setSubject(subject);
    message.setText(body);
    mailSender.send(message);
  }
}
