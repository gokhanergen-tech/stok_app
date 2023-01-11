package com.stok.stokapp.Usecases.Security.Services;

import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Security.Entities.RefreshToken;
import com.stok.stokapp.Usecases.Security.Repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(rollbackFor = {Exception.class})
@Service
public class RefreshTokenService {

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(String token, User user){

        RefreshToken refreshToken=refreshTokenRepository.findByUserId(user.getId()).orElse(null);

        if(refreshToken!=null)
            refreshToken.setRefreshToken(token);
        else{
            refreshToken=new RefreshToken();
            refreshToken.setUser(user);
            refreshToken.setRefreshToken(token);
        }

        refreshTokenRepository.save(refreshToken);
    }

    public void removeRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

    public RefreshToken getRefreshToken(Long userId) throws Exception{
        Optional<RefreshToken> refreshToken=refreshTokenRepository.findByUserId(userId);
        RefreshToken refreshTokenObject=refreshToken.orElse(null);
        if(refreshToken==null)
            throw new Exception();
        return refreshTokenObject;
    }
}
