package com.stok.stokapp.Usecases.Security.Utils;

import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Security.Entities.Authority;
import com.stok.stokapp.Usecases.Security.Services.UserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class AuthenticateProcesses {
    
    public static void authenticate(UserDetailsService userDetailsService, HttpServletRequest request, String username) {
            User user =(User) userDetailsService.loadUserByUsername(username);

            var tokenAuth=new UsernamePasswordAuthenticationToken(user.getUsername(),null, user.getAuthorities().stream().map(authority -> {
                authority.setAuthority(authority.getAuthority().trim());
                return authority;
            }).collect(Collectors.toSet()));
            tokenAuth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(tokenAuth);
    }
    
}
