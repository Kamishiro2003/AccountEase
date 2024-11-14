package com.manage.accounts.ease.infrastructure.adapter.in.rest.controller.auth;

import com.manage.accounts.ease.application.usecase.user.UserDetailServiceImpl;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.adapter.auth.AuthenticationAdapter;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.auth.AuthLoginRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.auth.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that manages authentication requests for the application.
 * Provides an endpoint for logging in users and generating JWT tokens.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final UserDetailServiceImpl detailService;

  private final AuthenticationAdapter adapter;

  /**
   * Endpoint for logging in users.
   * Takes user credentials, authenticates the user, and returns a JWT token in the response.
   *
   * @param request the login request containing username and password
   * @return a ResponseEntity with {@link AuthResponse} containing authentication details and JWT token
   */
  @PostMapping("/log-in")
  public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthLoginRequest request) {

    return new ResponseEntity<>(
        adapter.toResponse(detailService.loginUser(adapter.authLoginRequestToDomain(request))),
        HttpStatus.OK
    );
  }
}
