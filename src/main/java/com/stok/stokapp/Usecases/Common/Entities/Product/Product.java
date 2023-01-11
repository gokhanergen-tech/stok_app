package com.stok.stokapp.Usecases.Common.Entities.Product;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.stok.stokapp.Usecases.Baseclasses.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "products")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "title",nullable = false,columnDefinition = "varchar(100)")
    private String title;

    @Column(name = "subtitle",nullable = false,columnDefinition = "varchar(150)")
    private String subtitle;

    @ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "category_id",nullable = false,foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key(category_id) references categories(id) on delete restrict on update cascade"))
    private Category category;

}
