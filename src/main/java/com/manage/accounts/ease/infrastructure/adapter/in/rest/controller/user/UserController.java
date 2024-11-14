package com.manage.accounts.ease.infrastructure.adapter.in.rest.controller.user;

import com.manage.accounts.ease.application.port.in.user.UserCreateUseCase;
import com.manage.accounts.ease.application.port.in.user.UserDeleteUseCase;
import com.manage.accounts.ease.application.port.in.user.UserRetrieveUseCase;
import com.manage.accounts.ease.application.port.in.user.UserUpdateUseCase;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.adapter.user.UserRestAdapter;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.user.UserCreateRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.request.user.UserUpdateRequest;
import com.manage.accounts.ease.infrastructure.adapter.in.rest.model.response.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing user-related operations.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserCreateUseCase createUseCase;

  private final UserRetrieveUseCase retrieveUseCase;

  private final UserUpdateUseCase updateUseCase;

  private final UserDeleteUseCase deleteUseCase;

  private final UserRestAdapter adapter;

  /**
   * Retrieves a user by its username.
   *
   * @param username the name of the user
   * @return the user details in a model response
   */
  @GetMapping("/{username}")
  public ResponseEntity<UserResponse> findByUsername(@PathVariable("username") String username) {
    return new ResponseEntity<>(adapter.toUserResponse(retrieveUseCase.findByUsername(username)),
        HttpStatus.OK
    );
  }

  /**
   * Creates a new user entry.
   *
   * @param createRequest the user creation request
   * @return the created user in a model response
   */
  @PostMapping("/")
  public ResponseEntity<UserResponse> saveOne(@Valid @RequestBody UserCreateRequest createRequest) {
    return new ResponseEntity<>(adapter.toUserResponse(
        createUseCase.createByOne(adapter.createRequestToAnimeModel(createRequest))),
        HttpStatus.CREATED
    );
  }

  /**
   * Updates an existing user by its username.
   *
   * @param username      the name of the user
   * @param updateRequest the user update request
   */
  @PutMapping("/{username}")
  public ResponseEntity<Void> updateByUsername(@PathVariable("username") String username,
      @Valid @RequestBody UserUpdateRequest updateRequest
  ) {
    updateUseCase.updateByUsername(username, adapter.updateRequestToAnimeModel(updateRequest));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Deletes a user by its username.
   *
   * @param username the name of the user.
   */
  @DeleteMapping("/{username}")
  public ResponseEntity<Void> deleteByUsername(@PathVariable("username") String username) {
    deleteUseCase.deleteByUsername(username);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
