package com.manage.accounts.ease.infrastructure.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.manage.accounts.ease.utils.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filter that intercepts each request to validate the JWT token,
 * extract user details, and set the security context.
 */
@RequiredArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;

  /**
   * Extracts the JWT token from the request header, validates it,
   * retrieves user details and authorities, and sets the authentication context.
   *
   * @param request     the HTTP request
   * @param response    the HTTP response
   * @param filterChain the filter chain for further processing
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs during filtering
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain
  ) throws ServletException, IOException {

    String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (jwtToken != null) {
      String token = jwtToken.substring(7);

      DecodedJWT decodedJwt = jwtUtils.validateToken(token);

      String username = jwtUtils.extractUsername(decodedJwt);
      String stringAuthorities = jwtUtils.getSpecificClaim(decodedJwt, "authorities").asString();

      Collection<? extends GrantedAuthority> authorities =
          AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);

      SecurityContext context = SecurityContextHolder.createEmptyContext();
      Authentication authenticationToken =
          new UsernamePasswordAuthenticationToken(username, null, authorities);
      context.setAuthentication(authenticationToken);
      SecurityContextHolder.setContext(context);
    }
    filterChain.doFilter(request, response);
  }
}
