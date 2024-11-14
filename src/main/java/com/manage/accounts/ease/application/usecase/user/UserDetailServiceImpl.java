package com.manage.accounts.ease.application.usecase.user;

import com.manage.accounts.ease.application.port.in.user.UserRetrieveUseCase;
import com.manage.accounts.ease.domain.exception.InvalidPasswordException;
import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.domain.model.auth.AuthLoginModel;
import com.manage.accounts.ease.domain.model.auth.AuthResponseModel;
import com.manage.accounts.ease.utils.jwt.JwtUtils;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for managing user authentication and authorization.
 * Implements {@link UserDetailsService} for loading user details by username and handling login logic.
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

  private final JwtUtils jwtUtils;

  private final PasswordEncoder passwordEncoder;

  private final UserRetrieveUseCase retrieveUseCase;

  /**
   * Loads a user by their username, including their authorities.
   *
   * @param username the username to load
   * @return a {@link UserDetails} object with user information and authorities
   * @throws UsernameNotFoundException if the user is not found
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserModel userModel = retrieveUseCase.findByUsername(username);
    SimpleGrantedAuthority authority =
        new SimpleGrantedAuthority("ROLE_".concat(userModel.getRole().name()));

    return new User(userModel.getUsername(), userModel.getPassword(), true, true, true, true,
        Collections.singletonList(authority)
    );
  }

  /**
   * Authenticates a user and generates an access token.
   *
   * @param authLoginRequest the login request containing username and password
   * @return an {@link AuthResponseModel} with the login status and access token
   */
  public AuthResponseModel loginUser(AuthLoginModel authLoginRequest) {

    String username = authLoginRequest.getUsername();
    String password = authLoginRequest.getPassword();

    Authentication authentication = this.authenticate(username, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String accessToken = jwtUtils.createToken(authentication);
    return AuthResponseModel.builder()
        .username(username)
        .message("User logged successfully")
        .jwt(accessToken)
        .status(true)
        .build();
  }

  /**
   * Authenticates a user by verifying the provided password.
   *
   * @param username the username of the user
   * @param password the plain-text password to verify
   * @return an {@link Authentication} object upon successful authentication
   * @throws InvalidPasswordException if the password is incorrect
   */
  public Authentication authenticate(String username, String password) {

    UserDetails userDetails = this.loadUserByUsername(username);

    if (!passwordEncoder.matches(password, userDetails.getPassword())) {
      throw new InvalidPasswordException();
    }

    return new UsernamePasswordAuthenticationToken(username, password,
        userDetails.getAuthorities()
    );
  }
}
