package com.manage.accounts.ease.infrastructure.security.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.manage.accounts.ease.domain.exception.InvalidTokenException;
import com.manage.accounts.ease.utils.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class JwtTokenValidatorTest {

  private final String jwtToken = "Bearer valid_jwt_token";

  private final String username = "testUser";

  private final String authorities = "ROLE_USER";

  @Mock
  private JwtUtils jwtUtils;

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private FilterChain filterChain;

  @Mock
  private DecodedJWT decodedJWT;

  @InjectMocks
  private JwtTokenValidator jwtTokenValidator;

  @BeforeEach
  void setUp() {

    SecurityContextHolder.clearContext();
  }

  @DisplayName("doFilterInternal - Valid JWT Token")
  @Test
  void doFilterInternal_WithValidJwtToken_ShouldSetAuthentication()
      throws ServletException, IOException {
    // Arrange
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(jwtToken);
    when(jwtUtils.validateToken("valid_jwt_token")).thenReturn(decodedJWT);
    when(jwtUtils.extractUsername(decodedJWT)).thenReturn(username);

    // Mock Claim to avoid lambda issues
    Claim authorityClaim = mock(Claim.class);
    when(authorityClaim.asString()).thenReturn(authorities);
    when(jwtUtils.getSpecificClaim(decodedJWT, "authorities")).thenReturn(authorityClaim);

    // Act
    jwtTokenValidator.doFilterInternal(request, response, filterChain);

    // Assert
    UsernamePasswordAuthenticationToken authentication =
        (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext()
            .getAuthentication();
    assertNotNull(authentication);
    assertEquals(username, authentication.getPrincipal());

    Collection<SimpleGrantedAuthority> expectedAuthorities =
        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    assertEquals(expectedAuthorities, authentication.getAuthorities());

    verify(filterChain, times(1)).doFilter(request, response);
  }


  @DisplayName("doFilterInternal - No JWT Token")
  @Test
  void doFilterInternal_NoJwtToken_ShouldNotSetAuthentication()
      throws ServletException, IOException {
    // Arrange
    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

    // Act
    jwtTokenValidator.doFilterInternal(request, response, filterChain);

    // Assert
    assertNull(SecurityContextHolder.getContext().getAuthentication());
    verify(filterChain, times(1)).doFilter(request, response);
  }

  @DisplayName("doFilterInternal - Invalid JWT Token")
  @Test
  void doFilterInternal_WithInvalidJwtToken_ShouldNotSetAuthentication()
      throws ServletException, IOException {
    // Arrange
    SecurityContextHolder.clearContext();  // Ensure no previous authentication is present
    String tokenWithoutBearer = "invalid_jwt_token";

    when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + tokenWithoutBearer);
    when(jwtUtils.validateToken(tokenWithoutBearer)).thenThrow(new InvalidTokenException());

    // Act
    jwtTokenValidator.doFilterInternal(request, response, filterChain);

    // Assert
    assertNull(SecurityContextHolder.getContext().getAuthentication(),
        "Security context should not contain an authentication object for invalid token"
    );
    verify(filterChain, times(1)).doFilter(request, response);
  }


}
