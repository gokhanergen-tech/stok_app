package com.stok.stokapp.Usecases.Security.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Short id;

    @Column(name = "authority",columnDefinition = "char(50)",unique = true,nullable = false)
    private String authority;

    @Override
    public boolean equals(Object obj) {
        if(obj==null)
            throw new NullPointerException();
        if(!(obj instanceof Authority))
            return false;

        return ((Authority)obj).getAuthority().equals(this.authority);
    }

}
