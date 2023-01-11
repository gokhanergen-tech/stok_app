package com.stok.stokapp.Usecases.Security.Repositories;

import com.stok.stokapp.Usecases.Security.Entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Short> {
    Optional<Authority> findByAuthority(String authority);
}
