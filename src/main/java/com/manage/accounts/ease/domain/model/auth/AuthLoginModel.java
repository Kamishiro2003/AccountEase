package com.manage.accounts.ease.domain.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model representing login credentials with username and password.
 * Includes Lombok annotations for getters, setters, no-args and all-args constructors,
 * and a builder for flexible object creation.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthLoginModel {
  private String username;
  private String password;
}
