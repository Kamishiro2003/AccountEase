package com.manage.accounts.ease.infrastructure.adapter.in.rest.adapter.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.user.UserCreateRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.user.UserUpdateRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.user.UserResponse;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.UserTestUtil;

class UserRestAdapterTests {

  private UserRestAdapter userRestAdapter;

  @BeforeEach
  void setUp() {

    userRestAdapter = new UserRestAdapter();
  }

  @DisplayName("Convert UserCreateRequest to UserModel")
  @Test
  void createRequestToUserModel_ShouldConvertToUserModel() {
    // Arrange
    UserCreateRequest createRequest = UserTestUtil.getValidUserCreateRequest();

    // Act
    UserModel userModel = userRestAdapter.createRequestToAnimeModel(createRequest);

    // Assert
    assertEquals(createRequest.getUsername(), userModel.getUsername());
    assertEquals(createRequest.getPassword(), userModel.getPassword());
    assertEquals(createRequest.getEmail(), userModel.getEmail());
    assertEquals(createRequest.getRole(), userModel.getRole());
  }

  @DisplayName("Convert UserUpdateRequest to UserModel")
  @Test
  void updateRequestToUserModel_ShouldConvertToUserModel() {
    // Arrange
    UserUpdateRequest updateRequest = UserTestUtil.getValidUserUpdateRequest();

    // Act
    UserModel userModel = userRestAdapter.updateRequestToAnimeModel(updateRequest);

    // Assert
    assertEquals(updateRequest.getUsername(), userModel.getUsername());
    assertEquals(updateRequest.getPassword(), userModel.getPassword());
    assertEquals(updateRequest.getEmail(), userModel.getEmail());
    assertEquals(updateRequest.getRole(), userModel.getRole());
    assertEquals(updateRequest.getIsEnabled(), userModel.isEnabled());
  }

  @DisplayName("Convert UserModel to UserResponse")
  @Test
  void toUserResponse_ShouldConvertToUserResponse() {
    // Arrange
    UserModel userModel = UserTestUtil.getValidUserModel();

    // Act
    UserResponse userResponse = userRestAdapter.toUserResponse(userModel);

    // Assert
    assertEquals(userModel.getUsername(), userResponse.getUsername());
    assertEquals(userModel.getEmail(), userResponse.getEmail());
    assertEquals(userModel.getRole(), userResponse.getRole());
    assertEquals(userModel.isEnabled(), userResponse.isEnabled());
    assertEquals(userModel.getCreatedAt(), userResponse.getCreatedAt());
    assertEquals(userModel.getUpdatedAt(), userResponse.getUpdatedAt());
  }

  @DisplayName("Convert List of UserModel to List of UserResponse")
  @Test
  void toUserResponseList_ShouldConvertListOfUserModelToListOfUserResponse() {
    // Arrange
    List<UserModel> userModelList =
        List.of(UserTestUtil.getValidUserModel(), UserTestUtil.getValidUserModel());

    // Act
    List<UserResponse> userResponseList = userRestAdapter.toUserResponseList(userModelList);

    // Assert
    assertEquals(userModelList.size(), userResponseList.size());

    userModelList.forEach(userModel -> {
      UserResponse userResponse = userResponseList.get(userModelList.indexOf(userModel));

      assertEquals(userModel.getUsername(), userResponse.getUsername());
      assertEquals(userModel.getEmail(), userResponse.getEmail());
      assertEquals(userModel.getRole(), userResponse.getRole());
      assertEquals(userModel.isEnabled(), userResponse.isEnabled());
      assertEquals(userModel.getCreatedAt(), userResponse.getCreatedAt());
      assertEquals(userModel.getUpdatedAt(), userResponse.getUpdatedAt());
    });
  }
}