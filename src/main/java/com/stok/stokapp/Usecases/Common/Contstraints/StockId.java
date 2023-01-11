package com.stok.stokapp.Usecases.Common.Contstraints;

import java.io.Serializable;
import java.util.Objects;

public class StockId implements Serializable {

    private long user;
    private long product;

    @Override
    public boolean equals(Object obj) {
        if(obj==null)
            throw new NullPointerException();

        if(obj==this)
            return true;

        if(!(obj instanceof StockId))
            return false;

        StockId stockId=(StockId) obj;
        return stockId.product==this.product&&stockId.user==this.user;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user,product);
    }
}
