package com.manage.accounts.ease.infrastructure.adapter.out.persistence.adapter;

import com.manage.accounts.ease.application.port.out.MailMessagePersistencePort;
import com.manage.accounts.ease.domain.model.MailMessageModel;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.repository.MailMessagePersistenceRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Persistence adapter for mail message operations.
 * Implements the {@link MailMessagePersistencePort} interface to interact with the database.
 */
@Component
@RequiredArgsConstructor
public class MailMessagePersistenceAdapter implements MailMessagePersistencePort {

  private final MailMessagePersistenceRepository repository;

  private final MailMessageAdapter adapter;

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<MailMessageModel> findByNameKey(String nameKey) {
    return repository.findByNameKey(nameKey).map(adapter::toDomain);
  }
}
