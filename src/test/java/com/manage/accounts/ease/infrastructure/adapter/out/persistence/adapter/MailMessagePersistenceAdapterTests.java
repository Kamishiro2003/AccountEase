package com.manage.accounts.ease.infrastructure.adapter.out.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.manage.accounts.ease.domain.model.MailMessageModel;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity.MailMessagePersistenceEntity;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.repository.MailMessagePersistenceRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.MailMessageTestUtil;

@ExtendWith(MockitoExtension.class)
class MailMessagePersistenceAdapterTests {

  private static final String NAME_KEY = "CREATE";

  @Mock
  private MailMessagePersistenceRepository repository;

  @Mock
  private MailMessageAdapter adapter;

  @InjectMocks
  private MailMessagePersistenceAdapter persistenceAdapter;

  private MailMessageModel model;

  private MailMessagePersistenceEntity entity;

  @BeforeEach
  void setUp() {

    model = MailMessageTestUtil.getValidModel();
    entity = MailMessageTestUtil.getValidEntity();
  }

  @DisplayName("Find by nameKey - MailMessage Exists")
  @Test
  void findByNameKey_WhenMailMessageExists_ShouldReturnMailMessageModel() {
    // Arrange
    when(repository.findByNameKey(anyString())).thenReturn(Optional.of(entity));
    when(adapter.toDomain(any(MailMessagePersistenceEntity.class))).thenReturn(model);

    // Act
    Optional<MailMessageModel> modelFound = persistenceAdapter.findByNameKey(NAME_KEY);

    // Assert
    assertTrue(modelFound.isPresent());
    assertEquals(model, modelFound.get());
  }

  @DisplayName("Find by nameKey - MailMessage Not Found")
  @Test
  void findByNameKey_WhenMailMessageDoesNotExist_ShouldReturnEmpty() {
    // Arrange
    when(repository.findByNameKey(anyString())).thenReturn(Optional.empty());

    // Act
    Optional<MailMessageModel> modelFound = persistenceAdapter.findByNameKey(NAME_KEY);

    // Assert
    assertFalse(modelFound.isPresent());
  }
}