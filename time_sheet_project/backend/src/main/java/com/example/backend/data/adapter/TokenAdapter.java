package com.example.backend.data.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.backend.core.core_repository.ITokenCoreRepository;
import com.example.backend.core.model.Token;
import com.example.backend.data.entity.TokenEntity;
import com.example.backend.data.repository.ITokenJpaRepository;
import com.example.backend.mapper.TokenMapper;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
public class TokenAdapter implements ITokenCoreRepository {

    private final ITokenJpaRepository jpaRepository;
    private final TokenMapper mapper;

    @Override
    public List<Token> findAllValidTokenByUser(Long id) {
        List<TokenEntity> tokenEntities = jpaRepository.findAllValidTokenByUser(id);
        List<Token> tokens = mapper.entitiesToModels(tokenEntities);
        return tokens;
    }

    @Override
    public Optional<Token> findByToken(String token) {
        Optional<TokenEntity> optionalEntity = jpaRepository.findByToken(token);
        Optional<Token> optional = mapper.optionalEntityToOptionalModel(optionalEntity);
        return optional;
    }

    @Override
    public Token save(Token storedToken) {
        TokenEntity storedTokenEntity = mapper.modelToEntity(storedToken);
        TokenEntity tokenEntity = jpaRepository.save(storedTokenEntity);
        Token token = mapper.entityToModel(tokenEntity);
        return token;
    }

    @Override
    public void saveAll(List<Token> validUserTokens) {

        List<TokenEntity> validUserTokenEntities = mapper.modelsToEntities(validUserTokens);
        jpaRepository.saveAll(validUserTokenEntities);
    }

}
