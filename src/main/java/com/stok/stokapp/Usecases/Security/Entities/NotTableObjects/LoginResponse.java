package com.stok.stokapp.Usecases.Security.Entities.NotTableObjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String refreshToken;
    private String accessToken;
}
