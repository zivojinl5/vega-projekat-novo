package com.example.backend.data.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backend.data.entity.TokenEntity;

public interface ITokenJpaRepository extends JpaRepository<TokenEntity, Long> {
    /*
     * @Query("SELECT t FROM tokens t JOIN t.user u WHERE u.id = :id AND (t.expired = false OR t.revoked = false)"
     * )
     * List<TokenEntity> findAllValidTokenByUser(@Param("id") Long id);
     */

    Optional<TokenEntity> findByToken(String token);

    @Query("SELECT t FROM TokenEntity t JOIN t.user u WHERE u.id = :id AND (t.expired = false OR t.revoked = false)")
    List<TokenEntity> findAllValidTokenByUser(@Param("id") Long id);
}
