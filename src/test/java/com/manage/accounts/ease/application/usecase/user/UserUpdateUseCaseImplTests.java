package com.manage.accounts.ease.application.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.EmailAlreadyExistException;
import com.manage.accounts.ease.domain.exception.UserNotFoundException;
import com.manage.accounts.ease.domain.exception.UsernameAlreadyExistException;
import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.utils.domain.RoleEnum;
import com.manage.accounts.ease.utils.mails.MailManager;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import utils.UserTestUtil;

@ExtendWith(MockitoExtension.class)
class UserUpdateUseCaseImplTests {

  @Mock
  private UserPersistencePort persistencePort;

  @Mock
  private MailManager manager;

  @Mock
  private JavaMailSender mailSender;

  @InjectMocks
  private UserUpdateUseCaseImpl updateUseCaseImpl;

  private UserModel user;

  private String username;

  @BeforeEach
  void setUp() {

    username = "Junior";
    user = UserTestUtil.getValidUserModel();
    lenient().doNothing().when(mailSender).send(any(SimpleMailMessage.class));
  }

  @DisplayName("Update user by username - User Exists")
  @Test
  void updateByUsername_WhenUserExists_ShouldUpdateUser() {
    // Arrange
    user.setUsername(username);
    user.setRole(RoleEnum.ADMIN);
    when(persistencePort.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    when(persistencePort.save(user)).thenReturn(user);
    doNothing().when(manager).sendAlertMail(user.getUsername(), user.getEmail(), "UPDATE");

    // Act
    updateUseCaseImpl.updateByUsername(user.getUsername(), user);

    // Assert
    assertEquals(username, user.getUsername());
    assertEquals(RoleEnum.ADMIN, user.getRole());
    verify(persistencePort, times(1)).save(user);
    verify(persistencePort, times(1)).findByUsername(user.getUsername());
    verify(manager, times(1)).sendAlertMail(user.getUsername(), user.getEmail(), "UPDATE");
  }


  @DisplayName("Update user by username - User Not Found")
  @Test
  void updateByUsername_WhenUserDoesNotExist_ShouldThrowUserNotFoundException() {
    // Arrange
    when(persistencePort.findByUsername(anyString())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(UserNotFoundException.class,
        () -> updateUseCaseImpl.updateByUsername(username, user)
    );
    verify(persistencePort, never()).save(any(UserModel.class));
  }

  @DisplayName("Find user by username - When Username Is Null")
  @Test
  void findByUsername_WhenUsernameIsNull_ShouldThrowIllegalArgumentException() {

    // Act & Assert
    assertThrows(IllegalArgumentException.class,
        () -> updateUseCaseImpl.updateByUsername(null, user)
    );
  }

  @DisplayName("Update user - Username Unique Constraint Violation")
  @Test
  void updateByUsername_WhenUsernameAlreadyExists_ShouldThrowUsernameAlreadyExistException() {
    // Arrange
    when(persistencePort.findByUsername(anyString())).thenReturn(Optional.of(user));
    when(persistencePort.save(any(UserModel.class))).thenThrow(
        new DataIntegrityViolationException("username_unique_constraint"));

    // Act & Assert
    assertThrows(UsernameAlreadyExistException.class,
        () -> updateUseCaseImpl.updateByUsername(username, user)
    );
    verify(persistencePort, times(1)).save(any(UserModel.class));
  }

  @DisplayName("Update user - Email Unique Constraint Violation")
  @Test
  void updateByUsername_WhenEmailAlreadyExists_ShouldThrowEmailAlreadyExistException() {
    // Arrange
    when(persistencePort.findByUsername(anyString())).thenReturn(Optional.of(user));
    when(persistencePort.save(any(UserModel.class))).thenThrow(
        new DataIntegrityViolationException("email_unique_constraint"));

    // Act & Assert
    assertThrows(EmailAlreadyExistException.class,
        () -> updateUseCaseImpl.updateByUsername(username, user)
    );
    verify(persistencePort, times(1)).save(any(UserModel.class));
  }

  @DisplayName("update user - Other DataIntegrityViolationException")
  @Test
  void updateByUsername_WhenOtherDataIntegrityViolation_ShouldRethrowOriginalException() {
    // Arrange
    DataIntegrityViolationException exception = new DataIntegrityViolationException("other_constraint_violation");
    when(persistencePort.findByUsername(anyString())).thenReturn(Optional.of(user));
    when(persistencePort.save(any(UserModel.class))).thenThrow(exception);

    // Act & Assert
    DataIntegrityViolationException thrownException =
        assertThrows(DataIntegrityViolationException.class, () -> updateUseCaseImpl.updateByUsername(username, user));

    // Assert
    assertEquals(exception, thrownException);
  }
}