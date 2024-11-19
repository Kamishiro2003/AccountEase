package com.manage.accounts.ease.application.port.in.mail.message;


import com.manage.accounts.ease.domain.model.MailMessageModel;

/**
 * Use case interface for retrieving mail message details.
 */
public interface MailMessageRetrieveUseCase {
  /**
   * Finds a mail message by its unique name key.
   *
   * @param nameKey the unique name key of the mail message
   * @return the mail message details as a {@link MailMessageModel}
   */
  MailMessageModel findByNameKey(String nameKey);
}
