package com.stok.stokapp.Usecases.Security.Filters;

import com.stok.stokapp.Usecases.Security.Services.CookieService;
import com.stok.stokapp.Usecases.Security.Services.JWTService;
import com.stok.stokapp.Usecases.Security.Services.UserDetailsService;
import com.stok.stokapp.Usecases.Security.Utils.AuthenticateProcesses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CookieService cookieService;

    @Value("${jwt.security.secret_key_accessToken}")
    private String accessSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

            String username = "";
            response.setContentType("application/json;");
            try {
              if(request.getCookies()!=null) {
                  Optional<Cookie> accessTokenCookie = cookieService.searchCookie(Arrays.stream(request.getCookies()), "accessToken");
                  Cookie accessToken = accessTokenCookie.orElse(null);
                  if (accessToken == null) {
                      throw new Exception();
                  }
                  String accessTokenGet = accessToken.getValue();
                  username = jwtService.extractUserName(accessTokenGet,accessSecretKey);
              }else{
                  throw new Exception();
              }

            }catch (Exception exception) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("{\"message\":\"You are not permitted!\"}");
                return;
            }

            //Save the user for Authentication
            try{
                AuthenticateProcesses.authenticate(userDetailsService,request,username);
            }catch (AuthenticationException usernameNotFoundException) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write("{\"message\":\"Authentication Error!\"}");
                return;
            }catch (Exception e){
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                response.getWriter().write("{\"message\":\"Server error!\"}");
                return;
            }

        filterChain.doFilter(request,response);


    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().matches("/api/login") || request.getRequestURI().matches("/api/register");
    }


}
