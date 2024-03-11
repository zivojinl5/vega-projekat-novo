package com.example.backend.core.model;

import com.example.backend.enums.TokenType;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Token {

  @Id
  @GeneratedValue
  public Integer id;

  @Column(unique = true)
  public String token;

  @Enumerated(EnumType.STRING)
  public TokenType tokenType = TokenType.BEARER;

  public boolean revoked;

  public boolean expired;

  public User user;

  public Token(User user, String jwtToken, TokenType tokenType, boolean expired, boolean revoked) {
    this.user = user;
    this.token = jwtToken;
    this.tokenType = tokenType;
    this.expired = expired;
    this.revoked = revoked;
  }
}
