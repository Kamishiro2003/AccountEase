package com.manage.accounts.ease.infrastructure.adapter.out.persistence.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.manage.accounts.ease.domain.model.MailMessageModel;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity.MailMessagePersistenceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.MailMessageTestUtil;

class MailMessageAdapterTests {

  private MailMessageAdapter adapter;

  private MailMessagePersistenceEntity entity;

  @BeforeEach
  void setUp() {
    adapter = new MailMessageAdapter();
    entity = MailMessageTestUtil.getValidEntity();
  }

  @DisplayName("Convert MailMessagePersistenceEntity to MailMessageModel")
  @Test
  void toDomain_ShouldConvertMailMessagePersistenceEntityToMailMessageModel() {
    // Act
    MailMessageModel modelConverted = adapter.toDomain(entity);

    // Assert
    assertEquals(entity.getId(), modelConverted.getId());
    assertEquals(entity.getNameKey(), modelConverted.getNameKey());
    assertEquals(entity.getSubject(), modelConverted.getSubject());
    assertEquals(entity.getBody(), modelConverted.getBody());
  }
}