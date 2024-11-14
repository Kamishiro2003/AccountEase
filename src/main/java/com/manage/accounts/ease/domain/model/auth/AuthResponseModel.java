package com.manage.accounts.ease.domain.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model representing authentication response.
 * Includes Lombok annotations for getters, setters, no-args and all-args constructors,
 * and a builder for flexible object creation.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthResponseModel {
  private String username;
  private String message;
  private String jwt;
  private Boolean status;
}
