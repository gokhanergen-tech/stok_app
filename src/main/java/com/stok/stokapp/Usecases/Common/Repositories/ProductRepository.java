package com.stok.stokapp.Usecases.Common.Repositories;

import com.stok.stokapp.Usecases.Common.Entities.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByIdNotIn(Set<Long> idsExcept);
}
