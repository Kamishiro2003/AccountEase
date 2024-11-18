package com.manage.accounts.ease.infrastructure.adapter.in.rest.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.manage.accounts.ease.application.port.in.user.UserCreateUseCase;
import com.manage.accounts.ease.application.port.in.user.UserDeleteUseCase;
import com.manage.accounts.ease.application.port.in.user.UserRetrieveUseCase;
import com.manage.accounts.ease.application.port.in.user.UserUpdateUseCase;
import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.adapter.user.UserRestAdapter;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.user.UserCreateRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.user.UserUpdateRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.user.UserResponse;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UserControllerTests {

  @Mock
  private UserCreateUseCase createUseCase;

  @Mock
  private UserRetrieveUseCase retrieveUseCase;

  @Mock
  private UserUpdateUseCase updateUseCase;

  @Mock
  private UserDeleteUseCase deleteUseCase;

  @Mock
  private UserRestAdapter adapter;

  @InjectMocks
  private UserController userController;

  private UserModel userModel;

  private UserCreateRequest createRequest;

  private UserUpdateRequest updateRequest;

  private UserResponse userResponse;

  @BeforeEach
  void setUp() {

    userModel = utils.UserTestUtil.getValidUserModel();
    createRequest = utils.UserTestUtil.getValidUserCreateRequest();
    updateRequest = utils.UserTestUtil.getValidUserUpdateRequest();
    userResponse = utils.UserTestUtil.getValidUserResponse();
  }

  @DisplayName("Get user by username - Success")
  @Test
  void findByUsername_ShouldReturnUserResponse() {
    // Arrange
    when(retrieveUseCase.findByUsername(anyString())).thenReturn(userModel);
    when(adapter.toUserResponse(userModel)).thenReturn(userResponse);

    // Act
    ResponseEntity<UserResponse> response = userController.findByUsername("username");

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(userResponse, response.getBody());
    verify(retrieveUseCase, times(1)).findByUsername(anyString());
  }

  @DisplayName("Find users by date range - Success")
  @Test
  void findUsersByDateRange_ShouldReturnUserResponses() {
    // Arrange
    LocalDate startDate = LocalDate.of(2023, 1, 1);
    LocalDate endDate = LocalDate.of(2023, 12, 31);
    List<UserModel> userModels = List.of(userModel, userModel);
    List<UserResponse> userResponses = List.of(userResponse, userResponse);

    when(retrieveUseCase.findUsersByDateRange(startDate, endDate)).thenReturn(userModels);
    when(adapter.toUserResponseList(userModels)).thenReturn(userResponses);

    // Act
    ResponseEntity<List<UserResponse>> response =
        userController.findUsersByDateRange(startDate, endDate);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(userResponses, response.getBody());
    verify(retrieveUseCase, times(1)).findUsersByDateRange(startDate, endDate);
  }


  @DisplayName("Create user - Success")
  @Test
  void saveOne_ShouldReturnCreatedUserResponse() {
    // Arrange
    when(adapter.createRequestToAnimeModel(createRequest)).thenReturn(userModel);
    when(createUseCase.createByOne(userModel)).thenReturn(userModel);
    when(adapter.toUserResponse(userModel)).thenReturn(userResponse);

    // Act
    ResponseEntity<UserResponse> response = userController.saveOne(createRequest);

    // Assert
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(userResponse, response.getBody());
    verify(createUseCase, times(1)).createByOne(any(UserModel.class));
  }

  @DisplayName("Update user by username - Success")
  @Test
  void updateByUsername_ShouldUpdateUserAndReturnNoContent() {
    // Arrange
    when(adapter.updateRequestToAnimeModel(updateRequest)).thenReturn(userModel);

    // Act
    ResponseEntity<Void> response = userController.updateByUsername("username", updateRequest);

    // Assert
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(updateUseCase, times(1)).updateByUsername(anyString(), any(UserModel.class));
  }

  @DisplayName("Delete user by username - Success")
  @Test
  void deleteByUsername_ShouldDeleteUserAndReturnNoContent() {
    // Act
    ResponseEntity<Void> response = userController.deleteByUsername("username");

    // Assert
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    verify(deleteUseCase, times(1)).deleteByUsername(anyString());
  }
}
