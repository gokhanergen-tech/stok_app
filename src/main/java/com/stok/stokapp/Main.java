package com.stok.stokapp;

import com.stok.stokapp.Usecases.Common.Entities.Product.Category;
import com.stok.stokapp.Usecases.Common.Entities.Product.Product;
import com.stok.stokapp.Usecases.Common.Entities.User.City;
import com.stok.stokapp.Usecases.Common.Repositories.CategoryRepository;
import com.stok.stokapp.Usecases.Common.Repositories.CityRepository;
import com.stok.stokapp.Usecases.Common.Repositories.ProductRepository;
import com.stok.stokapp.Usecases.Security.Entities.Authority;
import com.stok.stokapp.Usecases.Security.Repositories.AuthorityRepository;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.Set;


@SpringBootApplication
@EnableJpaAuditing
@EnableJpaRepositories
public class Main {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @PostConstruct
    private void init(){
        if(authorityRepository.count()==0){
            authorityRepository.save(new Authority(null,"ROLE_USER"));
            authorityRepository.save(new Authority(null,"ROLE_ADMIN"));
        }

        if(categoryRepository.count()==0){
            categoryRepository.save(new Category(null,"Gıda",null));
        }

        Category category=categoryRepository.findAll().get(0);




        if(productRepository.count()==0){
            for (int i = 100; i < 102; i++) {
                Product product=new Product(null,"Peynir"+i,"Deneme ürün"+i,category);
                category.getProducts().add(product);

                categoryRepository.save(category);

            }
        }


        if(cityRepository.count()==0){
            cityRepository.save(new City(null,"Niğde"));
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}
