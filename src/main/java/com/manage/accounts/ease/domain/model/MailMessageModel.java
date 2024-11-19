package com.manage.accounts.ease.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a mail message with details such as an ID, key name, subject, and body content.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MailMessageModel {

  /**
   * Unique identifier for the mail message.
   */
  private String id;

  /**
   * Key name used to identify the type or context of the mail message.
   */
  private String nameKey;

  /**
   * Subject line of the mail message.
   */
  private String subject;

  /**
   * Body content of the mail message.
   */
  private String body;
}
