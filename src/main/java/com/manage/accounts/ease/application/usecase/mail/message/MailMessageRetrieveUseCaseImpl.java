package com.manage.accounts.ease.application.usecase.mail.message;

import com.manage.accounts.ease.application.port.in.mail.message.MailMessageRetrieveUseCase;
import com.manage.accounts.ease.application.port.out.MailMessagePersistencePort;
import com.manage.accounts.ease.domain.exception.MissingParameterException;
import com.manage.accounts.ease.domain.exception.ObjectNotFoundException;
import com.manage.accounts.ease.domain.model.MailMessageModel;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class that implements the use case for retrieving mail messages.
 */
@Service
@RequiredArgsConstructor
public class MailMessageRetrieveUseCaseImpl implements MailMessageRetrieveUseCase {

  private final MailMessagePersistencePort persistencePort;

  private static final String NAME = "nameKey";

  /**
   * {@inheritDoc}
   */
  @Override
  public MailMessageModel findByNameKey(String nameKey) {
    if (nameKey != null) {
      Optional<MailMessageModel> model = persistencePort.findByNameKey(nameKey);
      if (model.isEmpty()) {
        throw new ObjectNotFoundException(NAME);
      }
      return model.get();
    }
    throw new MissingParameterException(NAME);
  }
}
