package com.stok.stokapp.Usecases.Common.Services;

import com.stok.stokapp.Usecases.Common.DTO.ProductDTO;
import com.stok.stokapp.Usecases.Common.DTO.StockDTO;
import com.stok.stokapp.Usecases.Common.Entities.NotTableEntities.StockRequest;
import com.stok.stokapp.Usecases.Common.Entities.Product.Product;
import com.stok.stokapp.Usecases.Common.Entities.Product.Stock;
import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Common.Mappers.MapperImplements.StockDTOImplements;
import com.stok.stokapp.Usecases.Common.Repositories.ProductRepository;
import com.stok.stokapp.Usecases.Common.Repositories.StockRepository;
import com.stok.stokapp.Usecases.Common.Repositories.UserRepository;
import com.stok.stokapp.Usecases.Common.Validations.StockValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    @Autowired
    private StockValidation stockValidation;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockDTOImplements stockDTOImplements;


    public void saveStockToUser(StockRequest stockRequest, User user) {
        stockValidation.validate(stockRequest);
        Optional<Product> productOptional=productRepository.findById(stockRequest.getProductId());
        Product product=productOptional.orElseThrow(()->new IllegalArgumentException("There is no such a product!"));


        Stock stock=new Stock(user,product,stockRequest.getStockCount());
        user.getStocks().add(stock);
        userRepository.save(user);
    }

    public List<StockDTO> getStocksForUser(User user) {
        return stockDTOImplements.mapToDTOList(List.copyOf(user.getStocks()));
    }
}
