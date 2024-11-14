package com.manage.accounts.ease.infrastructure.adapter.out.persistence.adapter;

import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity.UserPersistenceEntity;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Adapter for converting between {@link UserModel} and {@link UserPersistenceEntity}.
 */
@Component
public class UserAdapter {

  /**
   * Converts an {@link UserModel} to a {@link UserPersistenceEntity} entity.
   *
   * @param userModel the model to convert
   * @return the persistence entity
   */
  public UserPersistenceEntity toPersistenceEntity(UserModel userModel) {
    return UserPersistenceEntity.builder()
        .id(userModel.getId())
        .username(userModel.getUsername())
        .password(userModel.getPassword())
        .email(userModel.getEmail())
        .role(userModel.getRole())
        .createdAt(userModel.getCreatedAt())
        .updatedAt(userModel.getUpdatedAt())
        .isEnabled(userModel.isEnabled())
        .build();
  }

  /**
   * Converts an {@link UserPersistenceEntity} entity to a {@link UserModel}.
   *
   * @param userPersistenceEntity the persistence entity to convert
   * @return the domain model
   */
  public UserModel toDomain(UserPersistenceEntity userPersistenceEntity) {
    return UserModel.builder()
        .id(userPersistenceEntity.getId())
        .username(userPersistenceEntity.getUsername())
        .password(userPersistenceEntity.getPassword())
        .email(userPersistenceEntity.getEmail())
        .role(userPersistenceEntity.getRole())
        .createdAt(userPersistenceEntity.getCreatedAt())
        .updatedAt(userPersistenceEntity.getUpdatedAt())
        .isEnabled(userPersistenceEntity.isEnabled())
        .build();
  }

  /**
   * Converts a list of {@link UserPersistenceEntity} entities to a list of {@link UserModel}.
   *
   * @param userEntityList the list of persistence entities
   * @return the list of domain models
   */
  public List<UserModel> toUserDomainList(List<UserPersistenceEntity> userEntityList) {
    return userEntityList.stream()
        .map(userPersistenceEntity -> UserModel.builder()
            .id(userPersistenceEntity.getId())
            .username(userPersistenceEntity.getUsername())
            .password(userPersistenceEntity.getPassword())
            .email(userPersistenceEntity.getEmail())
            .role(userPersistenceEntity.getRole())
            .createdAt(userPersistenceEntity.getCreatedAt())
            .updatedAt(userPersistenceEntity.getUpdatedAt())
            .isEnabled(userPersistenceEntity.isEnabled())
            .build())
        .toList();
  }
}
