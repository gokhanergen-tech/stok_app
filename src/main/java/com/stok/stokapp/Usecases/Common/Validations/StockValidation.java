package com.stok.stokapp.Usecases.Common.Validations;

import com.stok.stokapp.Usecases.Common.Entities.NotTableEntities.StockRequest;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class StockValidation {

    public void validate(StockRequest stockRequest){
        if(stockRequest.getStockCount()==null){
            throw new IllegalArgumentException("Null stock count object error!");
        }

        if(stockRequest.getProductId()==null){
            throw new IllegalArgumentException("Null product id object error!");
        }
    }


}
