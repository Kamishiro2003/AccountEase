package com.manage.accounts.ease.infrastructure.adapter.out.persistence.repository;

import com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity.MailMessagePersistenceEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing and managing {@link MailMessagePersistenceEntity}
 * data in the database.
 */
public interface MailMessagePersistenceRepository
    extends JpaRepository<MailMessagePersistenceEntity, String> {

  /**
   * Finds a mail message entity by its name key.
   *
   * @param nameKey the unique key associated with the mail message
   * @return an {@link Optional} containing the found entity, or empty if not found
   */
  Optional<MailMessagePersistenceEntity> findByNameKey(String nameKey);
}
