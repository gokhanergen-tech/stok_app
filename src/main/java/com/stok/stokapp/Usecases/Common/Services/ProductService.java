package com.stok.stokapp.Usecases.Common.Services;

import com.stok.stokapp.Usecases.Common.DTO.ProductDTO;
import com.stok.stokapp.Usecases.Common.Entities.Product.Product;
import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Common.Mappers.MapperImplements.ProductDTOImplements;
import com.stok.stokapp.Usecases.Common.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDTOImplements productDTOImplements;

    public List<ProductDTO> getProducts(User authUser) {
        Set<Long> ids=authUser.getStocks().stream()
                .map(stock -> stock.getProduct())
                .map(product -> product.getId()).collect(Collectors.toSet());
        ids.add(-1l);
        List<Product> products=productRepository.findByIdNotIn(ids);

        System.out.println(products.size());
        return productDTOImplements.mapToDTOList(products);
    }
}
