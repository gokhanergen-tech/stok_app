package com.stok.stokapp.Usecases.Common.Entities.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Table(name = "categories")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "category",nullable = false,unique = true)
    private String category;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "category",cascade = {CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REFRESH})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Product> products;

}
