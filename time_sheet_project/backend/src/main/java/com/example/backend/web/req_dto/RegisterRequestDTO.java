package com.example.backend.web.req_dto;

import com.example.backend.enums.Role;
import com.example.backend.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

  public String name;
  public String username;
  public String password;
  public String email;
  public UserStatus userStatus;
  public Role role;
}

;