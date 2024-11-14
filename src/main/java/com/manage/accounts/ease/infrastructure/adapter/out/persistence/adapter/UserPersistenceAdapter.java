package com.manage.accounts.ease.infrastructure.adapter.out.persistence.adapter;

import com.manage.accounts.ease.application.port.out.UserPersistencePort;
import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.infrastructure.adapter.out.persistence.repository.UserPersistenceRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Adapter for user persistence operations, implementing {@link UserPersistencePort}.
 */
@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

  private final UserPersistenceRepository repository;

  private final UserAdapter adapter;

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<UserModel> findByUsername(String username) {
    return repository.findByUsername(username).map(adapter::toDomain);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<UserModel> findAll() {
    return adapter.toUserDomainList(repository.findAll());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserModel save(UserModel user) {
    return adapter.toDomain(repository.save(adapter.toPersistenceEntity(user)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteOne(String username) {
    repository.deleteByUsername(username);
  }
}
