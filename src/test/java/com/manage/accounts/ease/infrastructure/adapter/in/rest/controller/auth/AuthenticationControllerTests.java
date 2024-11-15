package com.manage.accounts.ease.infrastructure.adapter.in.rest.controller.auth;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.accounts.ease.application.usecase.user.UserDetailServiceImpl;
import com.manage.accounts.ease.domain.exception.InvalidPasswordException;
import com.manage.accounts.ease.domain.model.auth.AuthLoginModel;
import com.manage.accounts.ease.domain.model.auth.AuthResponseModel;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.adapter.auth.AuthenticationAdapter;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.controller.application.GlobalControllerAdvice;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.auth.AuthLoginRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.auth.AuthResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import utils.UserTestUtil;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTests {

  @Mock
  private UserDetailServiceImpl detailService;

  @Mock
  private AuthenticationAdapter adapter;

  @InjectMocks
  private AuthenticationController authenticationController;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  private AuthLoginRequest authLoginRequest;

  private AuthLoginModel authLoginModel;

  private AuthResponse authResponse;

  private AuthResponseModel authResponseModel;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(authenticationController)
        .setControllerAdvice(new GlobalControllerAdvice())
        .build();
    objectMapper = new ObjectMapper();

    authLoginRequest = UserTestUtil.getValidAuthLogin();
    authLoginModel = UserTestUtil.getValidAuthLoginModel();
    authResponse = UserTestUtil.getValidAuthResponse();
    authResponseModel = UserTestUtil.getValidAuthResponseModel();
  }

  @DisplayName("Login - Successful Authentication")
  @Test
  void login_ShouldReturnAuthResponseWithStatusOk() {
    // Arrange
    when(adapter.authLoginRequestToDomain(authLoginRequest)).thenReturn(authLoginModel);
    when(detailService.loginUser(authLoginModel)).thenReturn(authResponseModel);
    when(adapter.toResponse(authResponseModel)).thenReturn(authResponse);

    // Act
    ResponseEntity<AuthResponse> response = authenticationController.login(authLoginRequest);

    // Assert
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(authResponse, response.getBody());
    verify(adapter, times(1)).authLoginRequestToDomain(any(AuthLoginRequest.class));
    verify(detailService, times(1)).loginUser(any());
    verify(adapter, times(1)).toResponse(any(AuthResponseModel.class));
  }

  @DisplayName("Login - Invalid Credentials")
  @Test
  void login_WhenInvalidCredentials_ShouldThrowUnauthorized() {
    // Arrange
    when(adapter.authLoginRequestToDomain(authLoginRequest)).thenReturn(authLoginModel);
    when(detailService.loginUser(authLoginModel)).thenThrow(new InvalidPasswordException());

    // Act & Assert
    assertThrows(InvalidPasswordException.class, () -> {
      authenticationController.login(authLoginRequest);
    });
    verify(adapter, times(1)).authLoginRequestToDomain(any(AuthLoginRequest.class));
    verify(detailService, times(1)).loginUser(any(AuthLoginModel.class));
  }

  @DisplayName("Login - Missing Username or Password")
  @Test
  void login_WhenMissingCredentials_ShouldReturnBadRequest() throws Exception {
    // Arrange
    AuthLoginRequest invalidRequest =
        AuthLoginRequest.builder().username(null).password(null).build();
    String requestBody = objectMapper.writeValueAsString(invalidRequest);

    // Act & Assert
    mockMvc.perform(
            post("/api/v1/auth/log-in").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("USER-INVALID-PARAMETERS"))
        .andExpect(jsonPath("$.message").value("Invalid user parameters in the request"))
        .andExpect(jsonPath("$.details", hasItem("Field username cannot be empty or null")))
        .andExpect(jsonPath("$.details", hasItem("Field password cannot be empty or null")));
  }

}
