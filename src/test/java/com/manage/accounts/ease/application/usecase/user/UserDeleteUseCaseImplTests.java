package com.manage.accounts.ease.application.usecase.user;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.UserNotFoundException;
import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.utils.mails.MailManager;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.UserTestUtil;

@ExtendWith(MockitoExtension.class)
class UserDeleteUseCaseImplTests {

  @Mock
  private UserPersistencePort persistencePort;

  @Mock
  private MailManager manager;

  @InjectMocks
  private UserDeleteUseCaseImpl userDeleteUseCase;

  private UserModel user;

  private String username;

  @BeforeEach
  void setUp() {

    username = "Junior";
    user = UserTestUtil.getValidUserModel();
  }

  @DisplayName("Delete user by username - When User Exists")
  @Test
  void deleteByUsername_WhenUserExists_ShouldDeleteUser() {
    // Arrange
    when(persistencePort.findByUsername(anyString())).thenReturn(Optional.of(user));
    doNothing().when(manager).sendAlertMail(anyString(), anyString(), anyString());

    // Act
    userDeleteUseCase.deleteByUsername(username);

    // Assert
    verify(persistencePort, times(1)).deleteOne(username);
    verify(persistencePort, times(1)).findByUsername(username);
    verify(manager, times(1)).sendAlertMail(username, user.getEmail(), "DELETE");
  }


  @DisplayName("Delete user by username - When User Does Not Exist")
  @Test
  void deleteByUsername_WhenUserDoesNotExist_ShouldThrowUserNotFoundException() {
    // Arrange
    when(persistencePort.findByUsername(username)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> userDeleteUseCase.deleteByUsername(username));
    verify(persistencePort, never()).deleteOne(username);
    verify(manager, never()).sendAlertMail(anyString(), anyString(), anyString());
  }

  @DisplayName("Delete user by username - When Username Is Null")
  @Test
  void deleteByUsername_WhenUsernameIsNull_ShouldThrowIllegalArgumentException() {
    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> userDeleteUseCase.deleteByUsername(null));
    verify(persistencePort, never()).findByUsername(anyString());
    verify(persistencePort, never()).deleteOne(anyString());
    verify(manager, never()).sendAlertMail(anyString(), anyString(), anyString());
  }
}
