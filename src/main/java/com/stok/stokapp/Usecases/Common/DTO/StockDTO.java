package com.stok.stokapp.Usecases.Common.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private ProductDTO productDTO;
    private long stockCount;
}
