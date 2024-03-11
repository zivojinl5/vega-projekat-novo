package com.example.backend.core.response_model;

import com.example.backend.core.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
  private User user;
  private String accessToken;

}
