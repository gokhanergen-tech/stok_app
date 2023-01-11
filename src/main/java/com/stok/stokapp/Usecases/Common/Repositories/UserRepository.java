package com.stok.stokapp.Usecases.Common.Repositories;

import com.stok.stokapp.Usecases.Common.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<UserDetails> findByUsername(String username);

    @Query(nativeQuery = true,value = "select (case when (select count(*) from users where username=?1)>0 then true else false end) as result")
    Boolean userExist(String username);


}
