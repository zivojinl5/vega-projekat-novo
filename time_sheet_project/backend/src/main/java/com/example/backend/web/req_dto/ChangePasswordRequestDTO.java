package com.example.backend.web.req_dto;

import lombok.Data;

@Data
public class ChangePasswordRequestDTO {
    public String currentPassword;
    public String newPassword;
    public String confirmationPassword;
}
