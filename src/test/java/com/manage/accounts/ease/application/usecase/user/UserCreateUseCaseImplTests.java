package com.manage.accounts.ease.application.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.EmailAlreadyExistException;
import com.manage.accounts.ease.domain.exception.UsernameAlreadyExistException;
import com.manage.accounts.ease.domain.model.UserModel;
import org.junit.jupiter.api.Assertions;
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
class UserCreateUseCaseImplTests {

  @Mock
  private UserPersistencePort persistencePort;

  @Mock
  private JavaMailSender mailSender;

  @InjectMocks
  private UserCreateUseCaseImpl createUseCase;

  private UserModel user;

  @BeforeEach
  void setUp() {

    user = UserTestUtil.getValidUserModel();
    lenient().doNothing().when(mailSender).send(any(SimpleMailMessage.class));
  }

  @DisplayName("Create user - Valid Data")
  @Test
  void createByOne_WhenUserDataIsValid_ShouldReturnUserCreated() {
    // Arrange
    when(persistencePort.save(any(UserModel.class))).thenReturn(user);

    // Act
    UserModel userCreated = createUseCase.createByOne(user);

    // Assert
    Assertions.assertNotNull(userCreated);
    verify(persistencePort, times(1)).save(any(UserModel.class));
  }

  @DisplayName("Create user - Username Unique Constraint Violation")
  @Test
  void createByOne_WhenUsernameAlreadyExists_ShouldThrowUsernameAlreadyExistException() {
    // Arrange
    when(persistencePort.save(any(UserModel.class))).thenThrow(
        new DataIntegrityViolationException("username_unique_constraint"));

    // Act & Assert
    assertThrows(UsernameAlreadyExistException.class, () -> createUseCase.createByOne(user));
    verify(persistencePort, times(1)).save(any(UserModel.class));
  }

  @DisplayName("Create user - Email Unique Constraint Violation")
  @Test
  void createByOne_WhenEmailAlreadyExists_ShouldThrowEmailAlreadyExistException() {
    // Arrange
    when(persistencePort.save(any(UserModel.class))).thenThrow(
        new DataIntegrityViolationException("email_unique_constraint"));

    // Act & Assert
    assertThrows(EmailAlreadyExistException.class, () -> createUseCase.createByOne(user));
    verify(persistencePort, times(1)).save(any(UserModel.class));
  }

  @DisplayName("Create user - Other DataIntegrityViolationException")
  @Test
  void createByOne_WhenOtherDataIntegrityViolation_ShouldRethrowOriginalException() {
    // Arrange
    DataIntegrityViolationException exception = new DataIntegrityViolationException("other_constraint_violation");
    when(persistencePort.save(any(UserModel.class))).thenThrow(exception);

    // Act & Assert
    DataIntegrityViolationException thrownException =
        assertThrows(DataIntegrityViolationException.class, () -> createUseCase.createByOne(user));

    // Assert
    assertEquals(exception, thrownException);
  }
}