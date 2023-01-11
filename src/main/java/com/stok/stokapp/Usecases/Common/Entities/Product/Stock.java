package com.stok.stokapp.Usecases.Common.Entities.Product;

import com.stok.stokapp.Usecases.Common.Contstraints.StockId;
import com.stok.stokapp.Usecases.Common.Entities.User.User;
import com.stok.stokapp.Usecases.Baseclasses.Base;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Table(name = "stocks")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(StockId.class)
public class Stock extends Base {

    @Id
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "user_id",nullable = false,foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key(user_id) references users(id) on delete cascade"))
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinColumn(name = "product_id",nullable = false,foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key(product_id) references users(id) on delete cascade"))
    private Product product;

    @Column(name = "stock_count",nullable = false)
    private Long stockCount=0l;

}
