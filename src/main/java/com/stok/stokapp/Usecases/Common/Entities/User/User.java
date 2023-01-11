package com.stok.stokapp.Usecases.Common.Entities.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stok.stokapp.Usecases.Baseclasses.Base;
import com.stok.stokapp.Usecases.Common.Entities.Product.Stock;
import com.stok.stokapp.Usecases.Security.Entities.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User extends Base implements UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Column(name = "name",
            nullable = false, columnDefinition = "varchar(100)")
    private String name;

    @Column(name = "surname",
            nullable = false, columnDefinition = "varchar(100)")
    private String surname;

    @Column(name = "username", nullable = false, columnDefinition = "varchar check(length(username)>=5)", unique = true)
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "varchar check(length(password)>=8)")
    private String password;

    @Column(name = "email", nullable = false, columnDefinition = "varchar", unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "city_id", nullable = false, foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key(city_id) references cities(id) on delete restrict on update cascade"))
    private City city;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "user", cascade = {CascadeType.ALL})
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Set<Stock> stocks;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "users_authorities",
            joinColumns = {@JoinColumn(name = "user_id", foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key(user_id) references users(id) on delete cascade"))},
            inverseJoinColumns = {@JoinColumn(name = "authority_id", foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key(authority_id) references authorities(id) on delete restrict"))})
    private Set<Authority> authorities;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

}
