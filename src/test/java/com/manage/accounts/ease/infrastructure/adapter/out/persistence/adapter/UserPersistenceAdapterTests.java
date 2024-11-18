package com.manage.accounts.ease.infrastructure.adapter.out.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity.UserPersistenceEntity;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.repository.UserPersistenceRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.UserTestUtil;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTests {
  @Mock
  private UserPersistenceRepository repository;

  @Mock
  private UserAdapter adapter;

  @InjectMocks
  private UserPersistenceAdapter persistenceAdapter;

  private UserModel userModel;
  private UserPersistenceEntity userEntity;

  @BeforeEach
  void setUp() {
    userModel = UserTestUtil.getValidUserModel();
    userEntity = UserTestUtil.getValidUserPersistenceEntity();
  }

  @DisplayName("Find by username - User Exists")
  @Test
  void findByUsername_WhenUserExists_ShouldReturnUserModel() {
    // Arrange
    when(repository.findByUsername(userModel.getUsername())).thenReturn(Optional.of(userEntity));
    when(adapter.toDomain(userEntity)).thenReturn(userModel);

    // Act
    Optional<UserModel> result = persistenceAdapter.findByUsername(userModel.getUsername());

    // Assert
    assertTrue(result.isPresent());
    assertEquals(userModel, result.get());
  }

  @DisplayName("Find by username - User Not Found")
  @Test
  void findByUsername_WhenUserDoesNotExist_ShouldReturnEmpty() {
    // Arrange
    when(repository.findByUsername(userModel.getUsername())).thenReturn(Optional.empty());

    // Act
    Optional<UserModel> result = persistenceAdapter.findByUsername(userModel.getUsername());

    // Assert
    assertFalse(result.isPresent());
  }

  @DisplayName("Find all users")
  @Test
  void findAll_ShouldReturnListOfUserModels() {
    // Arrange
    List<UserPersistenceEntity> userEntities = List.of(userEntity, userEntity);
    List<UserModel> userModels = List.of(userModel, userModel);

    when(repository.findAll()).thenReturn(userEntities);
    when(adapter.toUserDomainList(userEntities)).thenReturn(userModels);

    // Act
    List<UserModel> result = persistenceAdapter.findAll();

    // Assert
    assertEquals(userModels.size(), result.size());
    assertEquals(userModels, result);
  }

  @DisplayName("Find users by date range")
  @Test
  void findUsersByDateRange_ShouldReturnListOfUserModels() {
    // Arrange
    LocalDate startDate = LocalDate.of(2024, 1, 1);
    LocalDate endDate = LocalDate.of(2024, 12, 31);

    List<UserPersistenceEntity> userEntities = List.of(userEntity, userEntity);
    List<UserModel> userModels = List.of(userModel, userModel);

    when(repository.findUsersByDateRange(startDate, endDate)).thenReturn(userEntities);
    when(adapter.toUserDomainList(userEntities)).thenReturn(userModels);

    // Act
    List<UserModel> result = persistenceAdapter.findUsersByDateRange(startDate, endDate);

    // Assert
    assertEquals(userModels.size(), result.size());
    assertEquals(userModels, result);
    verify(repository, times(1)).findUsersByDateRange(startDate, endDate);
    verify(adapter, times(1)).toUserDomainList(userEntities);
  }


  @DisplayName("Save user")
  @Test
  void save_ShouldConvertAndSaveUserModel() {
    // Arrange
    when(adapter.toPersistenceEntity(userModel)).thenReturn(userEntity);
    when(repository.save(userEntity)).thenReturn(userEntity);
    when(adapter.toDomain(userEntity)).thenReturn(userModel);

    // Act
    UserModel result = persistenceAdapter.save(userModel);

    // Assert
    assertEquals(userModel, result);
    verify(repository, times(1)).save(userEntity);
  }

  @DisplayName("Delete user by username")
  @Test
  void deleteOne_ShouldDeleteUserByUsername() {
    // Act
    persistenceAdapter.deleteOne(userModel.getUsername());

    // Assert
    verify(repository, times(1)).deleteByUsername(userModel.getUsername());
  }
}