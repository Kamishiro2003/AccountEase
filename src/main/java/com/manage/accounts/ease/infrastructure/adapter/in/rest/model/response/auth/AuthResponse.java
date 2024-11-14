package com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.auth;

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
public class AuthResponse {
  private String username;
  private String message;
  private String jwt;
  private Boolean status;
}
