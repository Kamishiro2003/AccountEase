package com.manage.accounts.ease.application.usecase.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.manage.accounts.ease.application.port.in.user.UserRetrieveUseCase;
import com.manage.accounts.ease.domain.exception.InvalidPasswordException;
import com.manage.accounts.ease.domain.exception.UserNotFoundException;
import com.manage.accounts.ease.domain.model.UserModel;
import com.manage.accounts.ease.domain.model.auth.AuthLoginModel;
import com.manage.accounts.ease.domain.model.auth.AuthResponseModel;
import com.manage.accounts.ease.utils.jwt.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import utils.UserTestUtil;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTests {

  @Mock
  private JwtUtils jwtUtils;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private UserRetrieveUseCase retrieveUseCase;

  @InjectMocks
  private UserDetailServiceImpl userDetailService;

  private UserModel testUser;

  private String username;

  @BeforeEach
  void setUp() {

    username = "Junior";
    testUser = UserTestUtil.getValidUserModel();
  }

  @DisplayName("Load user by username - Valid User")
  @Test
  void loadUserByUsername_WhenUserExists_ShouldReturnUserDetails() {
    // Arrange
    when(retrieveUseCase.findByUsername(username)).thenReturn(testUser);

    // Act
    UserDetails userDetails = userDetailService.loadUserByUsername(username);

    // Assert
    assertNotNull(userDetails);
  }

  @DisplayName("Load user by username - User Not Found")
  @Test
  void loadUserByUsername_WhenUserNotFound_ShouldThrowUsernameNotFoundException() {
    // Arrange
    when(retrieveUseCase.findByUsername(username)).thenThrow(new UserNotFoundException());

    // Act & Assert
    assertThrows(UserNotFoundException.class, () -> userDetailService.loadUserByUsername(username));
  }

  @DisplayName("Login user - Valid credentials")
  @Test
  void loginUser_WhenCredentialsAreValid_ShouldReturnAuthResponseModel() {
    // Arrange
    AuthLoginModel loginRequest = new AuthLoginModel(username, "1234");
    when(retrieveUseCase.findByUsername(anyString())).thenReturn(testUser);
    when(passwordEncoder.matches("1234", testUser.getPassword())).thenReturn(true);
    when(jwtUtils.createToken(any(Authentication.class))).thenReturn("testToken");

    // Act
    AuthResponseModel response = userDetailService.loginUser(loginRequest);

    // Assert
    assertNotNull(response);
    assertEquals(username, response.getUsername());
    assertEquals("User logged successfully", response.getMessage());
    assertEquals("testToken", response.getJwt());
    assertTrue(response.getStatus());
  }

  @DisplayName("Authenticate user - Incorrect password")
  @Test
  void authenticate_WhenPasswordIsIncorrect_ShouldThrowInvalidPasswordException() {
    // Arrange
    String incorrectPassword = "4321";

    when(retrieveUseCase.findByUsername(username)).thenReturn(testUser);
    when(passwordEncoder.matches(incorrectPassword, testUser.getPassword())).thenReturn(false);

    // Act & Assert
    assertThrows(InvalidPasswordException.class,
        () -> userDetailService.authenticate(username, incorrectPassword)
    );
  }


  @DisplayName("Authenticate user - Correct password")
  @Test
  void authenticate_WhenPasswordIsCorrect_ShouldReturnAuthentication() {
    // Arrange
    String correctPassword = "1234";
    when(retrieveUseCase.findByUsername(username)).thenReturn(testUser);
    when(passwordEncoder.matches(correctPassword, testUser.getPassword())).thenReturn(true);

    // Act
    Authentication authentication = userDetailService.authenticate(username, correctPassword);

    // Assert
    assertNotNull(authentication);
    assertEquals(username, authentication.getName());
  }
}