package com.example.backend.core.service_Implementation;

import com.example.backend.config.JwtService;
import com.example.backend.core.core_repository.ITokenCoreRepository;
import com.example.backend.core.core_repository.IUserCoreRepository;
import com.example.backend.core.model.Token;
import com.example.backend.core.model.User;
import com.example.backend.core.req_model.AuthenticationRequest;
import com.example.backend.core.req_model.RegisterRequest;
import com.example.backend.core.response_model.AuthenticationResponse;
import com.example.backend.core.service.IAuthenticationService;
import com.example.backend.enums.TokenType;
import com.example.backend.mapper.UserMapper;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements IAuthenticationService {
  private final IUserCoreRepository userRepository;
  private final ITokenCoreRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserMapper mapper;

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()));
    User user = userRepository.findByUsername(request.getUsername());
    String jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return new AuthenticationResponse(user, jwtToken);
  }

  public void saveUserToken(User user, String jwtToken) {
    Token token = new Token(user, jwtToken, TokenType.BEARER, false, false);
    tokenRepository.save(token);
  }

  public void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response) {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String username;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    username = jwtService.extractUsername(refreshToken);
    if (username != null) {
      var user = this.userRepository.findByUsername(username);
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        AuthenticationResponse authResponse = new AuthenticationResponse(user, accessToken);

        try {
          new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        } catch (StreamWriteException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (DatabindException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }

}
