package com.stok.stokapp.Usecases.Common.Repositories;

import com.stok.stokapp.Usecases.Common.Entities.Product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
