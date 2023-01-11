package com.stok.stokapp.Usecases.Common.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String city;
    private List<StockDTO> stocks;
}
