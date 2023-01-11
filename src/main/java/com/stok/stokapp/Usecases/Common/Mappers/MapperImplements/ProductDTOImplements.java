package com.stok.stokapp.Usecases.Common.Mappers.MapperImplements;

import com.stok.stokapp.Usecases.Common.DTO.ProductDTO;
import com.stok.stokapp.Usecases.Common.Entities.Product.Product;
import com.stok.stokapp.Usecases.Common.Mappers.MapperInterfaces.IMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductDTOImplements implements IMapper<ProductDTO, Product> {

    @Override
    public ProductDTO mapToDTO(Product object) {
        if(isObjectNull(object))
            return null;
        return new ProductDTO(object.getId(),object.getTitle(),object.getSubtitle(),object.getCategory());
    }

    @Override
    public Product mapToNotDTO(ProductDTO objectDTO) {
        if(isObjectNull(objectDTO))
            return null;
        return new Product(objectDTO.getId(), objectDTO.getTitle(),objectDTO.getSubtitle(),objectDTO.getCategory());
    }

    @Override
    public List<ProductDTO> mapToDTOList(List<Product> objectList) {
        if(isObjectNull(objectList))
            return null;
        return objectList.stream().map(object->mapToDTO(object)).collect(Collectors.toList());
    }

    @Override
    public List<Product> mapToNotDTOList(List<ProductDTO> objectDTOList) {
        if(isObjectNull(objectDTOList))
            return null;
        return objectDTOList.stream().map(object->mapToNotDTO(object)).collect(Collectors.toList());
    }
}
