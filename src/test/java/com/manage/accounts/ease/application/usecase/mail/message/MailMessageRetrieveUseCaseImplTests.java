package com.manage.accounts.ease.application.usecase.mail.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.manage.accounts.ease.application.port.out.MailMessagePersistencePort;
import com.manage.accounts.ease.domain.exception.MissingParameterException;
import com.manage.accounts.ease.domain.exception.ObjectNotFoundException;
import com.manage.accounts.ease.domain.model.MailMessageModel;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.MailMessageTestUtil;

@ExtendWith(MockitoExtension.class)
class MailMessageRetrieveUseCaseImplTests {

  private static final String NAME_KEY = "CREATE";

  @Mock
  private MailMessagePersistencePort persistencePort;

  @InjectMocks
  private MailMessageRetrieveUseCaseImpl retrieveUseCase;

  private MailMessageModel model;

  @BeforeEach
  void setUp() {

    model = MailMessageTestUtil.getValidModel();
  }

  @DisplayName("Find MailMessageModel by nameKey - MailMessageModel Exists")
  @Test
  void findByNameKey_WhenMailMessageModelExists_ShouldReturnMailMessageModel() {
    // Arrange
    when(persistencePort.findByNameKey(anyString())).thenReturn(Optional.of(model));

    // Act
    MailMessageModel mailMessageModelFound = retrieveUseCase.findByNameKey(NAME_KEY);

    // Assert
    assertNotNull(mailMessageModelFound);
    assertEquals(NAME_KEY, mailMessageModelFound.getNameKey());
    verify(persistencePort, times(1)).findByNameKey(anyString());
  }

  @DisplayName("Find MailMessageModel by nameKey - MailMessageModel Not Found")
  @Test
  void findByNameKey_WhenMailMessageModelDoesNotExist_ShouldThrowObjectNotFoundException() {
    // Arrange
    when(persistencePort.findByNameKey(anyString())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ObjectNotFoundException.class, () -> retrieveUseCase.findByNameKey(NAME_KEY));
    verify(persistencePort, times(1)).findByNameKey(anyString());
  }

  @DisplayName("Find MailMessageModel by nameKey - When nameKey Is Null")
  @Test
  void findByNameKey_WhenNameKeyIsNull_ShouldThrowMissingParameterException() {
    // Act & Assert
    assertThrows(MissingParameterException.class, () -> retrieveUseCase.findByNameKey(null));
    verifyNoInteractions(persistencePort);
  }
}