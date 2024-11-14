package com.manage.accounts.ease.domain.model;

import com.manage.accounts.ease.utils.domain.RoleEnum;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a user with essential account information.
 *
 * <p>This class uses Lombok to automatically generate getters, setters,
 * a full-arguments constructor, and a builder for easy object creation.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserModel {

  /**
   * Unique ID of the user.
   */
  private String id;

  /**
   * Username associated with the user's account.
   */
  private String username;

  /**
   * Password for user authentication.
   */
  private String password;

  /**
   * email associated with the user's account.
   */
  private String email;

  /**
   * Role associated with the user.
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
   */
  private boolean isEnabled;
}
