package com.manage.accounts.ease.infrastructure.adapter.out.persistence.adapter;

import com.manage.accounts.ease.domain.model.MailMessageModel;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity.MailMessagePersistenceEntity;
import org.springframework.stereotype.Component;

/**
 * Adapter component for converting {@link MailMessagePersistenceEntity}
 * to {@link MailMessageModel}.
 */
@Component
public class MailMessageAdapter {
  /**
   * Converts a {@link MailMessagePersistenceEntity} to a {@link MailMessageModel}.
   *
   * @param entity the persistence entity representing the mail message
   * @return the domain model representing the mail message
   */
  public MailMessageModel toDomain(MailMessagePersistenceEntity entity) {
    return MailMessageModel.builder()
        .id(entity.getId())
        .nameKey(entity.getNameKey())
        .subject(entity.getSubject())
        .body(entity.getBody())
        .build();
  }
}
