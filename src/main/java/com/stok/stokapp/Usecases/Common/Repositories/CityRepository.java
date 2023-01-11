package com.stok.stokapp.Usecases.Common.Repositories;

import com.stok.stokapp.Usecases.Common.Entities.User.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City,Short> {
    Optional<City> findByCity(String city);
}
