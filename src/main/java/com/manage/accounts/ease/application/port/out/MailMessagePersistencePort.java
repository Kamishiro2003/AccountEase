package com.manage.accounts.ease.application.port.out;

import com.manage.accounts.ease.domain.model.MailMessageModel;
import java.util.Optional;

/**
 * Port interface for persistence operations related to mail messages.
 */
public interface MailMessagePersistencePort {
  /**
   * Finds a mail message by its unique name key.
   *
   * @param nameKey the unique name key of the mail message
   * @return an {@link Optional} containing the mail message details if found, otherwise empty
   */
  Optional<MailMessageModel> findByNameKey(String nameKey);
}
