package com.stok.stokapp.Usecases.Security.Services;

import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Common.Mappers.MapperImplements.UserDTOImplements;
import com.stok.stokapp.Usecases.Security.Entities.NotTableObjects.LoginRequest;
import com.stok.stokapp.Usecases.Security.Entities.NotTableObjects.LoginResponse;
import com.stok.stokapp.Usecases.Security.Validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    private UserDTOImplements userDTOImplements;

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserValidation userValidation;

    public LoginResponse login(LoginRequest loginRequest,
                               Integer refreshTokenExpiration,
                               Integer accessTokenExpiration,
                               String accessTokenSecretKey,
                               String refreshTokenSecretKey){
        String username=loginRequest.getUsername();
        String password=loginRequest.getPassword();

        userValidation.validatePassword(password);
        userValidation.validateUsername(username);

        Authentication authentication=new UsernamePasswordAuthenticationToken(username,password);

        daoAuthenticationProvider.authenticate(authentication);

        String accessToken=jwtService.generateJWT(accessTokenSecretKey,accessTokenExpiration,authentication);
        String refreshToken=jwtService.generateJWT(refreshTokenSecretKey,refreshTokenExpiration,authentication);
        return new LoginResponse(refreshToken,accessToken);
    }

    public User getLoginUser(){
        Authentication authentication=getAuthentication();
        String username=authentication.getName();
        return (User)userDetailsService.loadUserByUsername(username);
    }

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public UserDTOImplements getUserDTOImplements() {
        return userDTOImplements;
    }
}
