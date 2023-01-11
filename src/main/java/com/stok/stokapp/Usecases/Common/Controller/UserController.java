package com.stok.stokapp.Usecases.Common.Controller;

import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Security.Entities.NotTableObjects.LoginRequest;
import com.stok.stokapp.Usecases.Security.Entities.NotTableObjects.LoginResponse;
import com.stok.stokapp.Usecases.Security.Services.CookieService;
import com.stok.stokapp.Usecases.Security.Services.LoginService;
import com.stok.stokapp.Usecases.Security.Services.RefreshTokenService;
import com.stok.stokapp.Usecases.Security.Services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value="/api")
public class UserController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.security.secret_key_accessToken}")
    private String accessTokenKey;
    @Value("${jwt.security.secret_key_refreshToken}")
    private String refreshTokenKey;

    @Value("${access_token_expiration}")
    private Integer accessTokenExpiration;
    @Value("${refresh_token_expiration}")
    private Integer refreshTokenExpiration;

    @PostMapping(value="/register")
    private ResponseEntity<Map<String,Object>> register(@RequestBody User user){
        Map<String,Object> response=new HashMap<>();
        short status= (short) HttpStatus.CREATED.value();
        try {
            userDetailsService.createUser(user);
            response.put("message","Created the user successfully!");
        }catch (IllegalArgumentException illegalArgumentException){
            status=400;
            response.put("message",illegalArgumentException.getMessage());
        }
        catch (Exception exception){
            status=500;
            response.put("message",exception);
        }

        return ResponseEntity.status(status).body(response);

    }

    @PostMapping(value="/login")
    private ResponseEntity<Map<String,Object>> login(HttpServletResponse httpServletResponse, @RequestBody LoginRequest loginRequest){
        Map<String,Object> response=new HashMap<>();
        short status=200;

        try {
            LoginResponse loginResponse=loginService.login(loginRequest,refreshTokenExpiration,accessTokenExpiration,accessTokenKey,refreshTokenKey);

            cookieService.addCookie(httpServletResponse,"accessToken",loginResponse.getAccessToken(),accessTokenExpiration);
            cookieService.addCookie(httpServletResponse,"refreshToken",loginResponse.getRefreshToken(),refreshTokenExpiration);

            User user=(User)userDetailsService.loadUserByUsername(loginRequest.getUsername());
            refreshTokenService.saveRefreshToken(loginResponse.getRefreshToken(),user);
            response.put("isAuth",true);
            response.put("user",loginService.getUserDTOImplements().mapToDTO(user));
        }catch (IllegalArgumentException illegalArgumentException){
            status=400;
            response.put("message","Password or username was wrong!");
        }catch (AuthenticationException authenticationException){
            status=401;
            response.put("message",authenticationException.getMessage());
        }catch (Exception e){
            status=500;
            response.put("message",e.getMessage());
        }
        finally {
            return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(response);
        }
    }

    @GetMapping(value="/user")
    public ResponseEntity<Map<String,Object>> authUserData(Authentication authentication){
        Map<String,Object> response=new HashMap<>();
        authentication.getAuthorities().forEach(grantedAuthority -> System.out.println(grantedAuthority.getAuthority()));
        short status=200;

        try{
            String username=authentication.getName();
            User user=(User) userDetailsService.loadUserByUsername(username);
            response.put("isAuth",true);
            response.put("user",loginService.getUserDTOImplements().mapToDTO(user));
        }catch (AuthenticationException authenticationException){
            response.put("message","Authentication error!");
            status=400;
        }
        catch (Exception e){
            status=500;
            response.put("message",e.getMessage());
        }

        return ResponseEntity.status(status).body(response);
    }
}
