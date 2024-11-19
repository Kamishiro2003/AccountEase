package com.manage.accounts.ease.application.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.exception.MissingParameterException;
import com.manage.accounts.ease.domain.exception.UserNotFoundException;
import com.manage.accounts.ease.domain.model.UserModel;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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

  private LocalDate startDate;

  private LocalDate endDate;

  private List<UserModel> userList;

  @BeforeEach
  void setUp() {

    username = "Junior";
    user = UserTestUtil.getValidUserModel();
    startDate = LocalDate.of(2023, 1, 1);
    endDate = LocalDate.of(2023, 12, 31);
    userList = List.of(user);
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
    verify(persistencePort, times(1)).findByUsername(anyString());
  }

  @DisplayName("Find user by username - User Not Found")
  @Test
  void findByUsername_WhenUserDoesNotExist_ShouldThrowUserNotFoundException() {
    // Arrange
    when(persistencePort.findByUsername(anyString())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> retrieveUseCaseImpl.findByUsername(username));
    verify(persistencePort, times(1)).findByUsername(anyString());
  }

  @DisplayName("Find user by username - When Username Is Null")
  @Test
  void findByUsername_WhenUsernameIsNull_ShouldThrowMissingParameterException() {
    // Act & Assert
    assertThrows(MissingParameterException.class, () -> retrieveUseCaseImpl.findByUsername(null));
    verifyNoInteractions(persistencePort);
  }

  // Tests for findUsersByDateRange
  @DisplayName("Find users by date range - Users Exist")
  @Test
  void findUsersByDateRange_WhenUsersExist_ShouldReturnUserList() {
    // Arrange
    when(persistencePort.findUsersByDateRange(any(LocalDate.class),
        any(LocalDate.class)
    )).thenReturn(userList);

    // Act
    List<UserModel> actualUsers = retrieveUseCaseImpl.findUsersByDateRange(startDate, endDate);

    // Assert
    assertEquals(userList, actualUsers);
    verify(persistencePort, times(1)).findUsersByDateRange(startDate, endDate);
  }

  @DisplayName("Find users by date range - No Users Found")
  @Test
  void findUsersByDateRange_WhenNoUsersExist_ShouldReturnEmptyList() {
    // Arrange
    when(persistencePort.findUsersByDateRange(any(LocalDate.class),
        any(LocalDate.class)
    )).thenReturn(List.of());

    // Act
    List<UserModel> actualUsers = retrieveUseCaseImpl.findUsersByDateRange(startDate, endDate);

    // Assert
    assertTrue(actualUsers.isEmpty());
    verify(persistencePort, times(1)).findUsersByDateRange(startDate, endDate);
  }

  @DisplayName("Find users by date range - Null Start or End Date")
  @Test
  void findUsersByDateRange_WhenStartOrEndDateIsNull_ShouldThrowMissingParameterException() {
    // Act & Assert
    assertThrows(MissingParameterException.class,
        () -> retrieveUseCaseImpl.findUsersByDateRange(null, endDate)
    );
    assertThrows(MissingParameterException.class,
        () -> retrieveUseCaseImpl.findUsersByDateRange(startDate, null)
    );
    verifyNoInteractions(persistencePort);
  }
}
