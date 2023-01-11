package com.stok.stokapp.Usecases.Security.Entities;

import com.stok.stokapp.Usecases.Common.Entities.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER,cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id",unique = true,nullable = false,foreignKey = @ForeignKey(foreignKeyDefinition = "foreign key(user_id) references users(id) on delete cascade"))
    private User user;

    @Column(name = "refresh_token",unique = true,columnDefinition = "varchar(2000)",nullable = false)
    private String refreshToken;

}
