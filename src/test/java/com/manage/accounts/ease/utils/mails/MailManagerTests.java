package com.manage.accounts.ease.utils.mails;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.manage.accounts.ease.application.usecase.mail.message.MailMessageRetrieveUseCaseImpl;
import com.manage.accounts.ease.domain.model.MailMessageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class MailManagerTests {

  private final String email = "test@example.com";

  private final String username = "TestUser";

  private final String role = "Admin";

  private final String nameKey = "WELCOME";

  @Mock
  private MailMessageRetrieveUseCaseImpl retrieveUseCase;

  @Mock
  private JavaMailSender mailSender;

  @InjectMocks
  private MailManager mailManager;

  private MailMessageModel mailMessageModel;

  @BeforeEach
  void setUp() {

    mailMessageModel = MailMessageModel.builder()
        .id("1")
        .nameKey(nameKey)
        .subject("Welcome, %s!")
        .body("Hello %s, welcome to our system. Your role is %s.")
        .build();
  }

  @DisplayName("Send Welcome Email - Success")
  @Test
  void sendWelcomeEmail_ShouldSendMailSuccessfully() {
    // Arrange
    when(retrieveUseCase.findByNameKey(nameKey)).thenReturn(mailMessageModel);

    // Act
    mailManager.sendWelcomeEmail(username, email, role, nameKey);

    // Assert
    verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    verify(retrieveUseCase, times(1)).findByNameKey(nameKey);
  }

  @DisplayName("Send Welcome Email - Template Not Found")
  @Test
  void sendWelcomeEmail_WhenTemplateNotFound_ShouldThrowException() {
    // Arrange
    when(retrieveUseCase.findByNameKey(nameKey)).thenThrow(
        new RuntimeException("Template not found"));

    // Act & Assert
    assertThrows(RuntimeException.class,
        () -> mailManager.sendWelcomeEmail(username, email, role, nameKey)
    );
    verify(mailSender, never()).send(any(SimpleMailMessage.class));
  }

  @DisplayName("Send Alert Mail - Success")
  @Test
  void sendAlertMail_ShouldSendMailSuccessfully() {
    // Arrange
    mailMessageModel.setSubject("Alert: Suspicious Login Attempt");
    mailMessageModel.setBody("Dear %s, there has been an alert on your account.");
    when(retrieveUseCase.findByNameKey(nameKey)).thenReturn(mailMessageModel);

    // Act
    mailManager.sendAlertMail(username, email, nameKey);

    // Assert
    verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    verify(retrieveUseCase, times(1)).findByNameKey(nameKey);
  }

  @DisplayName("Send Alert Mail - Template Not Found")
  @Test
  void sendAlertMail_WhenTemplateNotFound_ShouldThrowException() {
    // Arrange
    when(retrieveUseCase.findByNameKey(nameKey)).thenThrow(
        new RuntimeException("Template not found"));

    // Act & Assert
    assertThrows(RuntimeException.class, () -> mailManager.sendAlertMail(username, email, nameKey));
    verify(mailSender, never()).send(any(SimpleMailMessage.class));
  }
}