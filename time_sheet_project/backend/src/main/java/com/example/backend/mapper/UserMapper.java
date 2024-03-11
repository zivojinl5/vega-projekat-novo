package com.example.backend.mapper;

import com.example.backend.core.model.User;
import com.example.backend.core.req_model.AuthenticationRequest;
import com.example.backend.core.req_model.ChangePasswordRequest;
import com.example.backend.core.req_model.RegisterRequest;
import com.example.backend.data.entity.UserEntity;
import com.example.backend.web.dto.UserDTO;
import com.example.backend.web.req_dto.AuthenticationRequestDTO;
import com.example.backend.web.req_dto.ChangePasswordRequestDTO;
import com.example.backend.web.req_dto.RegisterRequestDTO;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ModelMapper modelMapper = new ModelMapper();

    public UserDTO modelToDTO(User model) {
        return modelMapper.map(model, UserDTO.class);

    }

    public User dTOToModel(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public UserEntity modelToEntity(User model) {
        return modelMapper.map(model, UserEntity.class);
    }

    public User entityToModel(UserEntity entity) {
        return modelMapper.map(entity, User.class);
    }

    public UserEntity modelToEntityForPatch(User source, UserEntity destination) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(source, destination);
        // Reset skipNullEnabled to its default value
        modelMapper.getConfiguration().setSkipNullEnabled(false);
        return destination;
    }

    public Page<User> entitiesPageToModelsPage(Page<UserEntity> entities) {
        List<User> List = entities.getContent().stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(List, entities.getPageable(), entities.getTotalElements());
    }

    public Page<UserDTO> mapModelsPageDTOsPage(Page<User> modelsPage) {
        List<UserDTO> models = modelsPage.getContent().stream()
                .map(model -> modelToDTO(model))
                .collect(Collectors.toList());

        return new PageImpl<>(models, modelsPage.getPageable(), modelsPage.getTotalElements());
    }

    public RegisterRequest registerReqDTOToRegisterReq(RegisterRequestDTO request) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(request.getName());
        registerRequest.setUsername(request.getUsername());
        registerRequest.setPassword(request.getPassword());
        registerRequest.setEmail(request.getEmail());
        registerRequest.setRole(request.getRole());
        registerRequest.setUserStatus(request.getUserStatus());

        return modelMapper.map(request, RegisterRequest.class);
    }

    public User registerRequestToUser(RegisterRequest registerRequest) {
        User user = new User();
        user.setName(registerRequest.getName());
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        user.setUserStatus(registerRequest.getUserStatus());
        user.setTokens(new ArrayList<>());
        return user;
    }

    public AuthenticationRequest authReqDTOToAuthReq(AuthenticationRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, AuthenticationRequest.class);

    }

    public ChangePasswordRequest ChangePassRegDTOToChangePassReq(ChangePasswordRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, ChangePasswordRequest.class);

    }
}
