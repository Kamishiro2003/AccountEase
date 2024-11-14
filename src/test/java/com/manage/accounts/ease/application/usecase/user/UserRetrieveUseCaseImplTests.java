package com.manage.accounts.ease.application.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.UserNotFoundException;
import com.manage.accounts.ease.domain.model.UserModel;
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
class UserRetrieveUseCaseImplTests {

  @Mock
  private UserPersistencePort persistencePort;

  @InjectMocks
  private UserRetrieveUseCaseImpl retrieveUseCaseImpl;

  private UserModel user;

  private String username;

  @BeforeEach
  void setUp() {
    username = "Junior";
    user = UserTestUtil.getValidUserModel();
  }

  @DisplayName("Find user by username - User Exists")
  @Test
  void findByUsername_WhenUserExists_ShouldReturnUser() {
    // Arrange
    when(persistencePort.findByUsername(anyString())).thenReturn(Optional.of(user));

    // Act
    UserModel actualUser = retrieveUseCaseImpl.findByUsername(user.getUsername());

    // Assert
    assertEquals(user, actualUser);
  }

  @DisplayName("Find user by username - User Not Found")
  @Test
  void findByUsername_WhenUserDoesNotExist_ShouldThrowUserNotFoundException() {
    // Arrange
    when(persistencePort.findByUsername(anyString())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> retrieveUseCaseImpl.findByUsername(username));
  }

  @DisplayName("Find user by username - When Username Is Null")
  @Test
  void findByUsername_WhenUsernameIsNull_ShouldThrowIllegalArgumentException() {

    // Act & Assert
    assertThrows(IllegalArgumentException.class, () -> retrieveUseCaseImpl.findByUsername(null));
  }
}