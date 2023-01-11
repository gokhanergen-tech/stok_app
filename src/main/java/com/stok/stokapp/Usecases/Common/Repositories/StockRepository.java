package com.stok.stokapp.Usecases.Common.Repositories;

import com.stok.stokapp.Usecases.Common.Entities.Product.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {
}
