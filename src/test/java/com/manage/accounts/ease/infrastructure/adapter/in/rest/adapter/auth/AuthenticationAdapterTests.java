package com.manage.accounts.ease.infrastructure.adapter.in.rest.adapter.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.manage.accounts.ease.domain.model.auth.AuthLoginModel;
import com.manage.accounts.ease.domain.model.auth.AuthResponseModel;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.auth.AuthLoginRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.auth.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AuthenticationAdapterTests {

  private AuthenticationAdapter adapter;

  @BeforeEach
  void setUp() {

    adapter = new AuthenticationAdapter();
  }

  @Test
  @DisplayName("Convert AuthLoginRequest to AuthLoginModel - Success")
  void authLoginRequestToDomain_ShouldConvertToDomainModel() {
    // Arrange
    AuthLoginRequest request =
        AuthLoginRequest.builder().username("testUser").password("testPassword").build();

    // Act
    AuthLoginModel result = adapter.authLoginRequestToDomain(request);

    // Assert
    assertEquals(request.getUsername(), result.getUsername(), "Username should match");
    assertEquals(request.getPassword(), result.getPassword(), "Password should match");
  }

  @Test
  @DisplayName("Convert AuthResponseModel to AuthResponse - Success")
  void toResponse_ShouldConvertToApiResponseModel() {
    // Arrange
    AuthResponseModel responseModel = AuthResponseModel.builder()
        .username("testUser")
        .message("Login successful")
        .jwt("sampleJwtToken")
        .status(true)
        .build();

    // Act
    AuthResponse result = adapter.toResponse(responseModel);

    // Assert
    assertEquals(responseModel.getUsername(), result.getUsername(), "Username should match");
    assertEquals(responseModel.getMessage(), result.getMessage(), "Message should match");
    assertEquals(responseModel.getJwt(), result.getJwt(), "JWT token should match");
    assertEquals(responseModel.getStatus(), result.getStatus(), "Status should match");
  }
}
