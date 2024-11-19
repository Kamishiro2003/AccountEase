package com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing the persistence structure for email messages.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
public class MailMessagePersistenceEntity {

  /**
   * Unique identifier for the mail message.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(updatable = false)
  private String id;

  /**
   * Key associated with the mail message, used for retrieval.
   */
  @Column(length = 250, nullable = false, updatable = false)
  private String nameKey;

  /**
   * Subject of the mail message.
   */
  @Column(length = 500, nullable = false, updatable = false)
  private String subject;

  /**
   * Body content of the mail message.
   */
  @Column(length = 1000, nullable = false, updatable = false)
  private String body;
}
