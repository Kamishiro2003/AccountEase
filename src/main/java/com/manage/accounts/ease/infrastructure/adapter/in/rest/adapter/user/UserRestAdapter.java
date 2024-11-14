package com.manage.accounts.ease.infrastructure.adapter.in.rest.adapter.user;

import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.user.UserCreateRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.user.UserUpdateRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.user.UserResponse;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Adapter for converting between REST request/response models and domain models.
 */
@Component
public class UserRestAdapter {

  /**
   * Converts a {@link UserCreateRequest} to an {@link UserModel}.
   *
   * @param createRequest the create request
   * @return the domain model
   */
  public UserModel createRequestToAnimeModel(UserCreateRequest createRequest) {
    return UserModel.builder()
        .username(createRequest.getUsername())
        .password(createRequest.getPassword())
        .email(createRequest.getEmail())
        .role(createRequest.getRole())
        .build();
  }

  /**
   * Converts a {@link UserUpdateRequest} to an {@link UserModel}.
   *
   * @param updateRequest the update request
   * @return the domain model
   */
  public UserModel updateRequestToAnimeModel(UserUpdateRequest updateRequest) {
    return UserModel.builder()
        .username(updateRequest.getUsername())
        .password(updateRequest.getPassword())
        .email(updateRequest.getEmail())
        .role(updateRequest.getRole())
        .isEnabled(updateRequest.getIsEnabled())
        .build();
  }

  /**
   * Converts a {@link UserModel} to a {@link UserResponse}.
   *
   * @param userModel the domain model
   * @return the response model
   */
  public UserResponse toUserResponse(UserModel userModel) {
    return UserResponse.builder()
        .username(userModel.getUsername())
        .email(userModel.getEmail())
        .role(userModel.getRole())
        .isEnabled(userModel.isEnabled())
        .createdAt(userModel.getCreatedAt())
        .updatedAt(userModel.getUpdatedAt())
        .build();
  }

  /**
   * Converts a list of {@link UserModel} to a list of {@link UserResponse}.
   *
   * @param userModelList the list of domain models
   * @return the list of response models
   */
  public List<UserResponse> toUserResponseList(List<UserModel> userModelList) {
    return userModelList.stream()
        .map(userModel -> UserResponse.builder()
            .username(userModel.getUsername())
            .email(userModel.getEmail())
            .role(userModel.getRole())
            .isEnabled(userModel.isEnabled())
            .createdAt(userModel.getCreatedAt())
            .updatedAt(userModel.getUpdatedAt())
            .build())
        .toList();
  }
}
