package com.manage.accounts.ease.infrastructure.adapter.out.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity.UserPersistenceEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.UserTestUtil;

class UserAdapterTests {

  private UserAdapter adapter;

  private UserModel userModel;

  private UserPersistenceEntity userPersistenceEntity;

  @BeforeEach
  void setUp() {

    adapter = new UserAdapter();
    userModel = UserTestUtil.getValidUserModel();
    userPersistenceEntity = UserTestUtil.getValidUserPersistenceEntity();
  }

  @DisplayName("Convert UserModel to UserPersistenceEntity")
  @Test
  void toPersistenceEntity_ShouldConvertUserModelToPersistenceEntity() {
    // Act
    UserPersistenceEntity userEntity = adapter.toPersistenceEntity(userModel);

    // Assert
    assertEquals(userModel.getId(), userEntity.getId());
    assertEquals(userModel.getUsername(), userEntity.getUsername());
    assertEquals(userModel.getPassword(), userEntity.getPassword());
    assertEquals(userModel.getRole(), userEntity.getRole());
    assertEquals(userModel.getCreatedAt(), userEntity.getCreatedAt());
    assertEquals(userModel.getUpdatedAt(), userEntity.getUpdatedAt());
    assertEquals(userModel.isEnabled(), userEntity.isEnabled());
  }

  @DisplayName("Convert UserPersistenceEntity to UserModel")
  @Test
  void toDomain_ShouldConvertUserPersistenceEntityToUserModel() {
    // Act
    UserModel user = adapter.toDomain(userPersistenceEntity);

    // Assert
    assertEquals(userPersistenceEntity.getId(), user.getId());
    assertEquals(userPersistenceEntity.getUsername(), user.getUsername());
    assertEquals(userPersistenceEntity.getPassword(), user.getPassword());
    assertEquals(userPersistenceEntity.getRole(), user.getRole());
    assertEquals(userPersistenceEntity.getCreatedAt(), user.getCreatedAt());
    assertEquals(userPersistenceEntity.getUpdatedAt(), user.getUpdatedAt());
    assertEquals(userPersistenceEntity.isEnabled(), user.isEnabled());
  }

  @DisplayName("Convert List of UserPersistenceEntity to List of UserModel")
  @Test
  void toUserDomainList_ShouldConvertUserPersistenceEntityListToUserModelList() {
    // Arrange
    List<UserPersistenceEntity> userEntityList =
        List.of(UserTestUtil.getValidUserPersistenceEntity(),
            UserTestUtil.getValidUserPersistenceEntity()
        );

    // Act
    List<UserModel> userModelList = adapter.toUserDomainList(userEntityList);

    // Assert
    assertEquals(userEntityList.size(), userModelList.size());

    userEntityList.forEach(userEntity -> {
      UserModel user = userModelList.get(userEntityList.indexOf(userEntity));

      assertEquals(userEntity.getId(), user.getId());
      assertEquals(userEntity.getUsername(), user.getUsername());
      assertEquals(userEntity.getPassword(), user.getPassword());
      assertEquals(userEntity.getRole(), user.getRole());
      assertEquals(userEntity.getCreatedAt(), user.getCreatedAt());
      assertEquals(userEntity.getUpdatedAt(), user.getUpdatedAt());
      assertEquals(userEntity.isEnabled(), user.isEnabled());
    });
  }
}