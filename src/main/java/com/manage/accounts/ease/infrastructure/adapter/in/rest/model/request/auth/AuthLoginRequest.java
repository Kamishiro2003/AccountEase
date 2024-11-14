package com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthLoginRequest {

  @NotBlank(message = "Field username cannot be empty or null")
  @Size(max = 250, message = "Field username must not exceed 250 characters")
  private String username;

  @NotBlank(message = "Field password cannot be empty or null")
  @Size(max = 100, message = "Field password must not exceed 100 characters")
  private String password;
}
