package com.stok.stokapp.Usecases.Security.Services;

import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Common.Repositories.CityRepository;
import com.stok.stokapp.Usecases.Common.Repositories.UserRepository;
import com.stok.stokapp.Usecases.Security.Repositories.AuthorityRepository;
import com.stok.stokapp.Usecases.Security.Validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Transactional(rollbackFor = {Exception.class})
@Service
public class UserDetailsService implements UserDetailsManager {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserValidation userValidation;

    @Override
    public void createUser(UserDetails userDetails) {
        User user=(User)userDetails;
        userValidation.validateUser(user);
        if(userExists(user.getUsername())){
            throw new IllegalArgumentException("User cannot be created!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(user.getAuthorities().stream().map(authority -> authorityRepository.findByAuthority(authority.getAuthority()).orElse(null)).filter(authority -> authority!=null).collect(Collectors.toSet()));
        user.setCity(cityRepository.findByCity(user.getCity().getCity()).get());

        userRepository.save(user);
    }

    @Override
    public void updateUser(UserDetails user) {

    }

    @Override
    public void deleteUser(String username) {

    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {

    }

    public boolean userExists(String username) {
        return userRepository.userExist(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails=userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found error!"));
        return userDetails;
    }
}
