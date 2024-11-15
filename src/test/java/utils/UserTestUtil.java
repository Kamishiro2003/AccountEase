package utils;

import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.domain.model.auth.AuthLoginModel;
import com.manage.accounts.ease.domain.model.auth.AuthResponseModel;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.auth.AuthLoginRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.user.UserCreateRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.user.UserUpdateRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.auth.AuthResponse;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.user.UserResponse;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity.UserPersistenceEntity;
import com.manage.accounts.ease.utils.domain.RoleEnum;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class UserTestUtil {

  public static UserModel getValidUserModel() {
    return UserModel.builder()
        .username("david")
        .password("1234")
        .email("Kamishiro2003@example.com")
        .role(RoleEnum.USER)
        .isEnabled(true)
        .createdAt(LocalDateTime.now(ZoneOffset.UTC))
        .updatedAt(LocalDateTime.now(ZoneOffset.UTC))
        .build();
  }

  public static UserPersistenceEntity getValidUserPersistenceEntity() {
    return UserPersistenceEntity.builder()
        .username("Kamishiro2003")
        .password("123423")
        .email("Kamishiro2003@example.com")
        .role(RoleEnum.ADMIN)
        .isEnabled(true)
        .createdAt(LocalDateTime.now(ZoneOffset.UTC))
        .updatedAt(LocalDateTime.now(ZoneOffset.UTC))
        .build();
  }

  public static UserCreateRequest getValidUserCreateRequest() {
    return UserCreateRequest.builder()
        .username("Kamishiro2003")
        .password("123423")
        .email("Kamishiro2003@example.com")
        .role(RoleEnum.ADMIN)
        .build();
  }

  public static UserUpdateRequest getValidUserUpdateRequest() {
    return UserUpdateRequest.builder()
        .username("Kamishiro2003")
        .password("123423")
        .email("Kamishiro2003@example.com")
        .role(RoleEnum.ADMIN)
        .isEnabled(true)
        .build();
  }

  public static UserResponse getValidUserResponse() {
    return UserResponse.builder()
        .username("Kamishiro2003")
        .email("Kamishiro2003@example.com")
        .role(RoleEnum.ADMIN)
        .isEnabled(true)
        .createdAt(LocalDateTime.now(ZoneOffset.UTC))
        .updatedAt(LocalDateTime.now(ZoneOffset.UTC))
        .build();
  }

  public static AuthLoginRequest getValidAuthLogin() {
    return AuthLoginRequest.builder()
        .username("Kamishiro2003")
        .password("123423")
        .build();
  }

  public static AuthLoginModel getValidAuthLoginModel() {
    return AuthLoginModel.builder()
        .username("Kamishiro2003")
        .password("123423")
        .build();
  }

  public static AuthResponse getValidAuthResponse() {
    return AuthResponse.builder()
        .username("Kamishiro2003")
        .message("User logged successfully")
        .jwt("mock-jwt-token")
        .status(true)
        .build();
  }

  public static AuthResponseModel getValidAuthResponseModel() {
    return AuthResponseModel.builder()
        .username("Kamishiro2003")
        .message("User logged successfully")
        .jwt("mock-jwt-token")
        .status(true)
        .build();
  }
}
