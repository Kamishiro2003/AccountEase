package com.manage.accounts.ease.infrastructure.adapter.in.rest.adapter.auth;

import com.manage.accounts.ease.domain.model.auth.AuthLoginModel;
import com.manage.accounts.ease.domain.model.auth.AuthResponseModel;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.auth.AuthLoginRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.auth.AuthResponse;
import org.springframework.stereotype.Component;

/**
 * Adapter for converting authentication-related request and response objects
 * between domain models and API models.
 */
@Component
public class AuthenticationAdapter {

  /**
   * Converts an {@link AuthLoginRequest} to an {@link AuthLoginModel}.
   *
   * @param request the authentication request from the client
   * @return the domain model for login credentials
   */
  public AuthLoginModel authLoginRequestToDomain(AuthLoginRequest request) {

    return AuthLoginModel.builder()
        .username(request.getUsername())
        .password(request.getPassword())
        .build();
  }

  /**
   * Converts an {@link AuthResponseModel} to an {@link AuthResponse}.
   *
   * @param response the domain model with authentication response data
   * @return the API response model with authentication details
   */
  public AuthResponse toResponse(AuthResponseModel response) {

    return AuthResponse.builder()
        .username(response.getUsername())
        .message(response.getMessage())
        .jwt(response.getJwt())
        .status(response.getStatus())
        .build();
  }
}
