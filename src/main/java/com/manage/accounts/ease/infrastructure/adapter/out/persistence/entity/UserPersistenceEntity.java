package com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity;

import com.manage.accounts.ease.utils.domain.RoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a user with essential account information.
 * This class is mapped to the "users" table in the database.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(name = "username_unique_constraint", columnNames = {"username"}),
    @UniqueConstraint(name = "email_unique_constraint", columnNames = {"email"})})
@NamedStoredProcedureQuery(name = "get_users_by_date_range",
    procedureName = "get_users_by_date_range", resultClasses = UserPersistenceEntity.class,
    parameters = {
      @StoredProcedureParameter(name = "start_date",
          mode = ParameterMode.IN,
          type = LocalDate.class),
      @StoredProcedureParameter(name = "end_date",
          mode = ParameterMode.IN,
          type = LocalDate.class)
    })
public class UserPersistenceEntity {

  /**
   * Unique ID of the user.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false)
  private String id;

  /**
   * Username associated with the user's account.
   */
  @Column(unique = true, length = 250, nullable = false)
  private String username;

  /**
   * Password for user authentication.
   */
  @Column(nullable = false, length = 255)
  private String password;

  /**
   * email associated with the user's account.
   */
  @Column(nullable = false, unique = true, length = 255)
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
