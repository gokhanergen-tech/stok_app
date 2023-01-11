package com.stok.stokapp.Usecases.Common.Entities.NotTableEntities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockRequest {
    private Long productId;
    private Long stockCount;
}
