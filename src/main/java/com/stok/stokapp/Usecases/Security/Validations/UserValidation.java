package com.stok.stokapp.Usecases.Security.Validations;

import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Common.Repositories.CityRepository;
import com.stok.stokapp.Usecases.Security.Entities.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class UserValidation {
    private Pattern passwordPattern=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
    private Pattern usernamePattern=Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,}$");
    private Pattern emailPattern=Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

    @Autowired
    private CityRepository cityRepository;

    public void validateUsername(String username){
        Optional<Boolean> optionalBoolean=Optional.ofNullable(username!=null&&passwordPattern.matcher(username).matches()?true:null);
        optionalBoolean.orElseThrow(()->new IllegalArgumentException("Invalid Password"));
    }

    public void validatePassword(String password){
        Optional<Boolean> optionalBoolean=Optional.ofNullable((password!=null&&usernamePattern.matcher(password).matches())?true:null);
        optionalBoolean.orElseThrow(()->new IllegalArgumentException("Invalid Password"));
    }

    private void throwError(String message){
        throw new IllegalArgumentException(message);
    }

    public void validateName(String name){
        if(name==null)
            throwError("Name cannot be null!");
        if(name.length()>100)
            throwError("Name must be lower than 101");
    }

    public void validateSurname(String surname){
        if(surname==null)
            throwError("Surname cannot be null!");
        if(surname.length()>100)
            throwError("Surname must be lower than 101");
    }

    public void validateEmail(String email){

        if(email==null||!emailPattern.matcher(email).matches()){
            throwError("Invalid mail!");
        }
    }

    public void validateAuthorities(Set<Authority> authoritySet){
        if(authoritySet==null||!Set.of("ROLE_USER","ROLE_ADMIN").containsAll(authoritySet.stream().map(authority -> authority.getAuthority()).collect(Collectors.toSet()))){
            throwError("Invalid authorities!");
        }
    }

    public void validateCity(String city){
           if(city==null||cityRepository.findByCity(city).orElse(null)==null)
               throwError("Invalid city!");
    }

    public void validateUser(User user){
        if(user==null)
            throwError("Null user object!");

        validateName(user.getName());
        validateSurname(user.getSurname());
        validateCity(user.getCity().getCity());
        validateUsername(user.getUsername());
        validatePassword(user.getPassword());
        validateAuthorities(user.getAuthorities());
        validateEmail(user.getEmail());
    }
}
