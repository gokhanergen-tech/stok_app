package com.stok.stokapp.Usecases.Common.Mappers.MapperImplements;

import com.stok.stokapp.Usecases.Common.DTO.StockDTO;
import com.stok.stokapp.Usecases.Common.Mappers.MapperInterfaces.IMapper;
import com.stok.stokapp.Usecases.Common.Entities.Product.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StockDTOImplements implements IMapper<StockDTO, Stock> {

    @Autowired
    private ProductDTOImplements productDTOImplements;

    @Override
    public StockDTO mapToDTO(Stock object) {
        if(isObjectNull(object))
            return null;
        return new StockDTO(productDTOImplements.mapToDTO(object.getProduct()),object.getStockCount());
    }

    @Override
    public Stock mapToNotDTO(StockDTO objectDTO) {
        if(isObjectNull(objectDTO))
            return null;
        return new Stock(null,productDTOImplements.mapToNotDTO(objectDTO.getProductDTO()),objectDTO.getStockCount());
    }

    @Override
    public List<StockDTO> mapToDTOList(List<Stock> objectList) {
        if(isObjectNull(objectList))
            return null;
        return objectList.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<Stock> mapToNotDTOList(List<StockDTO> objectDTOList) {
        if(isObjectNull(objectDTOList))
            return null;
        return objectDTOList.stream().map(this::mapToNotDTO).collect(Collectors.toList());
    }
}
