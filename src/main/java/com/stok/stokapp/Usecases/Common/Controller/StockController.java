package com.stok.stokapp.Usecases.Common.Controller;


import com.stok.stokapp.Usecases.Common.DTO.ProductDTO;
import com.stok.stokapp.Usecases.Common.DTO.StockDTO;
import com.stok.stokapp.Usecases.Common.Entities.NotTableEntities.StockRequest;
import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Common.Services.StockService;
import com.stok.stokapp.Usecases.Security.Services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "/stock")
    private ResponseEntity<Map<String,Object>> createStock(@RequestBody StockRequest stockRequest, Authentication authentication){
        Map<String,Object> response=new HashMap<>();
        short status=200;
        try {
            User user=(User)userDetailsService.loadUserByUsername(authentication.getName());
           stockService.saveStockToUser(stockRequest,user);
           response.put("message","Saved the stock successfully!");
        }catch (IllegalArgumentException illegalArgumentException){
            response.put("message",illegalArgumentException.getMessage());
            status=400;
        }catch (Exception e){
            response.put("message","Server side error!");
            status=500;
        }

        return ResponseEntity.status(status).body(response);

    }

    @GetMapping(value = "/user/stocks")
    private ResponseEntity<Map<String,Object>> getProductsForUsers(Authentication authentication){
        Map<String,Object> response=new HashMap<>();
        short status=200;
        try {

            User user=(User)userDetailsService.loadUserByUsername(authentication.getName());
            List<StockDTO> stockDTOList=stockService.getStocksForUser(user);

            response.put("products",stockDTOList);
            response.put("total",stockDTOList.size());
        } catch (Exception e){
            status=500;
            response.put("message","Server side error!");
        }

        return ResponseEntity.status(status).body(response);
    }
}
