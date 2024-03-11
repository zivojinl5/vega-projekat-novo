package com.example.backend.mapper;

import com.example.backend.core.model.Token;
import com.example.backend.core.req_model.AuthenticationRequest;
import com.example.backend.core.req_model.ChangePasswordRequest;
import com.example.backend.core.req_model.RegisterRequest;
import com.example.backend.data.entity.TokenEntity;
import com.example.backend.web.req_dto.AuthenticationRequestDTO;
import com.example.backend.web.req_dto.ChangePasswordRequestDTO;
import com.example.backend.web.req_dto.RegisterRequestDTO;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class TokenMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    /*
     * public TokenDTO modelToDTO(Token model) {
     * return modelMapper.map(model, TokenDTO.class);
     * 
     * }
     */
    /*
     * public Token dTOToModel(TokenDTO dto) {
     * return modelMapper.map(dto, Token.class);
     * }
     */

    public TokenEntity modelToEntity(Token model) {
        return modelMapper.map(model, TokenEntity.class);
    }

    public Token entityToModel(TokenEntity entity) {
        return modelMapper.map(entity, Token.class);
    }

    public TokenEntity modelToEntityForPatch(Token source, TokenEntity destination) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(source, destination);
        // Reset skipNullEnabled to its default value
        modelMapper.getConfiguration().setSkipNullEnabled(false);
        return destination;
    }

    public Page<Token> entitiesPageToModelsPage(Page<TokenEntity> entities) {
        List<Token> List = entities.getContent().stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());

        return new PageImpl<>(List, entities.getPageable(), entities.getTotalElements());
    }

    /*
     * public Page<TokenDTO> mapModelsPageDTOsPage(Page<Token> modelsPage) {
     * List<TokenDTO> models = modelsPage.getContent().stream()
     * .map(model -> modelToDTO(model))
     * .collect(Collectors.toList());
     * 
     * return new PageImpl<>(models, modelsPage.getPageable(),
     * modelsPage.getTotalElements());
     * }
     */

    public RegisterRequest registerReqDTOToRegisterReq(RegisterRequestDTO request) {
        return modelMapper.map(request, RegisterRequest.class);
    }

    public Token registerRequestToToken(RegisterRequest request) {
        return modelMapper.map(request, Token.class);
    }

    public AuthenticationRequest authReqDTOToAuthReq(AuthenticationRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, AuthenticationRequest.class);

    }

    public ChangePasswordRequest ChangePassRegDTOToChangePassReq(ChangePasswordRequestDTO requestDTO) {
        return modelMapper.map(requestDTO, ChangePasswordRequest.class);

    }

    public List<Token> entitiesToModels(List<TokenEntity> entities) {
        return entities.stream()
                .map(entity -> entityToModel(entity))
                .collect(Collectors.toList());
    }

    public List<TokenEntity> modelsToEntities(List<Token> models) {
        return models.stream()
                .map(model -> modelToEntity(model))
                .collect(Collectors.toList());
    }

    public Optional<Token> optionalEntityToOptionalModel(Optional<TokenEntity> optionalEntity) {
        return optionalEntity.map(this::entityToModel);

    }

}
