package com.manage.accounts.ease.utils.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.manage.accounts.ease.domain.exception.InvalidTokenException;
import com.manage.accounts.ease.utils.domain.RoleEnum;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

  private final String username = "Junior";

  private final String userAuthority = "ROLE_" + RoleEnum.USER;

  @Mock
  private Authentication authentication;

  @Mock
  private GrantedAuthority authority;

  @InjectMocks
  private JwtUtils jwtUtils;

  private final String privateKey = "YourTestSecretKey";

  private final String userGenerator = "TestIssuer";

  @BeforeEach
  void setUp() {

    ReflectionTestUtils.setField(jwtUtils, "privateKey", privateKey);
    ReflectionTestUtils.setField(jwtUtils, "userGenerator", userGenerator);
  }

  @DisplayName("Create token - Successful Token Generation")
  @Test
  void createToken_ShouldReturnValidJwtToken() {
    // Arrange
    Collection<GrantedAuthority> authorities =
        Collections.singletonList(new SimpleGrantedAuthority(userAuthority));
    doReturn(username).when(authentication).getPrincipal();
    doReturn(authorities).when(authentication).getAuthorities();

    // Act
    String token = jwtUtils.createToken(authentication);

    // Assert
    assertNotNull(token);
    assertFalse(token.isEmpty());
  }


  @DisplayName("Validate token - Valid Token")
  @Test
  void validateToken_WhenTokenIsValid_ShouldReturnDecodedJwt() {
    // Arrange
    Algorithm algorithm = Algorithm.HMAC256(privateKey);
    String token = JWT.create()
        .withIssuer(userGenerator)
        .withSubject(username)
        .withClaim("authorities", userAuthority)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
        .withJWTId(UUID.randomUUID().toString())
        .withNotBefore(new Date())
        .sign(algorithm);

    // Act
    DecodedJWT decodedJWT = jwtUtils.validateToken(token);

    // Assert
    assertNotNull(decodedJWT);
    assertEquals(username, decodedJWT.getSubject());
  }

  @DisplayName("Validate token - Invalid Token")
  @Test
  void validateToken_WhenTokenIsInvalid_ShouldThrowInvalidTokenException() {
    // Arrange
    String invalidToken = "invalidToken";

    // Act & Assert
    assertThrows(InvalidTokenException.class, () -> jwtUtils.validateToken(invalidToken));
  }


  @DisplayName("Extract Username - Successful Extraction")
  @Test
  void extractUsername_ShouldReturnUsername() {
    // Arrange
    Algorithm algorithm = Algorithm.HMAC256(privateKey);
    String token = JWT.create()
        .withIssuer(userGenerator)
        .withSubject(username)
        .withIssuedAt(new Date())
        .sign(algorithm);
    DecodedJWT decodedJWT = jwtUtils.validateToken(token);

    // Act
    String extractedUsername = jwtUtils.extractUsername(decodedJWT);

    // Assert
    assertEquals(username, extractedUsername);
  }


  @DisplayName("Get Specific Claim - Should Return Claim Value")
  @Test
  void getSpecificClaim_ShouldReturnClaimValue() {
    // Arrange
    String authorityClaim = "ROLE_USER";
    Algorithm algorithm = Algorithm.HMAC256(privateKey);
    String token = JWT.create()
        .withIssuer(userGenerator)
        .withSubject(username)
        .withClaim("authorities", authorityClaim)
        .withIssuedAt(new Date())
        .sign(algorithm);
    DecodedJWT decodedJWT = jwtUtils.validateToken(token);

    // Act
    String claimValue = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

    // Assert
    assertEquals(authorityClaim, claimValue);
  }

}
