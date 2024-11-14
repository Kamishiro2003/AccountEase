package com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.user;

import com.manage.accounts.ease.utils.domain.RoleEnum;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents the response model for user data in the REST API.
 * This class contains essential information about a user, including their username,
 * email, role, timestamps for creation and last update, and account status.
 *
 * <p>This class uses Lombok annotations to automatically generate getters, setters,
 * a full-argument constructor, and a builder for flexible object creation.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserResponse {

  /**
   * The username of the user.
   */
  private String username;

  /**
   * The email address of the user.
   */
  private String email;

  /**
   * The role assigned to the user (e.g., ADMIN, USER).
   */
  private RoleEnum role;

  /**
   * Timestamp indicating when the user account was created.
   */
  private LocalDateTime createdAt;

  /**
   * Timestamp indicating when the user account was last updated.
   */
  private LocalDateTime updatedAt;

  /**
   * Status indicating if the user account is enabled.
   * A value of {@code true} means the account is active, while {@code false} means it is disabled.
   */
  private boolean isEnabled;
}

