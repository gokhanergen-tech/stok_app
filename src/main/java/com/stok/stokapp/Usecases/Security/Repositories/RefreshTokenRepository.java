package com.stok.stokapp.Usecases.Security.Repositories;

import com.stok.stokapp.Usecases.Security.Entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query(nativeQuery = true,value = "select * from refresh_tokens  where user_id=?1")
    public Optional<RefreshToken> findByUserId(Long userId);

    public void deleteByRefreshToken(String refreshToken);
}
