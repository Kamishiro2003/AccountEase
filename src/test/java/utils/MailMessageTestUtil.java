package utils;

import com.manage.accounts.ease.domain.model.MailMessageModel;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.entity.MailMessagePersistenceEntity;

public class MailMessageTestUtil {

  public static MailMessageModel getValidModel() {

    return MailMessageModel.builder()
        .nameKey("CREATE")
        .subject("subject example")
        .body("body example")
        .build();
  }

  public static MailMessagePersistenceEntity getValidEntity() {
    return MailMessagePersistenceEntity.builder()
        .nameKey("CREATE")
        .subject("subject example")
        .body("body example")
        .build();
  }
}
