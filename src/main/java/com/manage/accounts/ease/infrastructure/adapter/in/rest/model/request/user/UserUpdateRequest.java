package com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.user;

import com.manage.accounts.ease.utils.domain.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a request to update a user, containing all necessary fields
 * for user updating such as username, password, email, role and isEnabled.
 *
 * <p>This class uses Lombok annotations to automatically generate getters, setters,
 * a full-argument constructor, and a builder for flexible object creation.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserUpdateRequest {

  /**
   * The username for the user.
   */
  @NotBlank(message = "Field username cannot be empty or null")
  @Size(max = 250, message = "Field username must not exceed 250 characters")
  private String username;

  /**
   * The password for the user.
   */
  @NotBlank(message = "Field password cannot be empty or null")
  @Size(max = 100, message = "Field password must not exceed 100 characters")
  private String password;

  /**
   * The email address of the user.
   */
  @NotBlank(message = "Field email cannot be empty or null")
  @Size(max = 250, message = "Field email must not exceed 250 characters")
  private String email;

  /**
   * The role assigned to the user (e.g., ADMIN, USER).
   */
  @NotNull(message = "Field role cannot be null")
  private RoleEnum role;

  /**
   * Status indicating if the user account is enabled.
   */
  @NotNull(message = "Field isEnabled cannot be null")
  private Boolean isEnabled;
}
