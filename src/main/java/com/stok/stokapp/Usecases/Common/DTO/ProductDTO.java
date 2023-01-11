package com.stok.stokapp.Usecases.Common.DTO;

import com.stok.stokapp.Usecases.Common.Entities.Product.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String title;
    private String subtitle;
    private Category category;
}
