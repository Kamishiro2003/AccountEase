package com.manage.accounts.ease.infrastructure.adapter.in.rest.adapter.auth;

import com.manage.accounts.ease.domain.model.auth.AuthLoginModel;
import com.manage.accounts.ease.domain.model.auth.AuthResponseModel;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.auth.AuthLoginRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.auth.AuthResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationAdapter {
  public AuthLoginModel authLoginRequestToDomain(AuthLoginRequest request) {
    return AuthLoginModel.builder()
        .username(request.getUsername())
        .password(request.getPassword())
        .build();
  }

  public AuthResponse toResponse(AuthResponseModel response) {
    return AuthResponse.builder()
        .username(response.getUsername())
        .message(response.getMessage())
        .jwt(response.getJwt())
        .status(response.getStatus())
        .build();
  }
}
