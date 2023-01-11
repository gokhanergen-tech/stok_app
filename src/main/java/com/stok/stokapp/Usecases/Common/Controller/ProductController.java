package com.stok.stokapp.Usecases.Common.Controller;

import com.stok.stokapp.Usecases.Common.DTO.ProductDTO;
import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Common.Services.ProductService;
import com.stok.stokapp.Usecases.Security.Services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(value = "/products")
    private ResponseEntity<Map<String,Object>> getProductsExceptUsers(Authentication authentication){
        Map<String,Object> response=new HashMap<>();
        short status=200;
        try {

            User user=(User)userDetailsService.loadUserByUsername(authentication.getName());
            List<ProductDTO> productDTOList=productService.getProducts(user);

            response.put("products",productDTOList);
            response.put("total",productDTOList.size());
        } catch (Exception e){
            status=500;
            response.put("message","Server side error!");
        }

        return ResponseEntity.status(status).body(response);
    }



}
