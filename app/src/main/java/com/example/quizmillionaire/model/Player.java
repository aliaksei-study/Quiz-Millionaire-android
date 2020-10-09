package com.example.quizmillionaire.model;

import com.example.quizmillionaire.model.enumeration.Role;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Player implements Serializable {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private Statistics statistics;

    public Player(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Player player = (Player) obj;
        return this.username != null && this.username.equals(player.username) && this.password != null &&
                this.password.equals(player.password) && this.role == player.role;
    }

    @Override
    public int hashCode() {
        return (31 * ((this.username == null) ? 0 : this.username.hashCode()) +
                31 * ((this.password == null) ? 0 : this.password.hashCode()) +
                31 * ((this.role == null) ? 0 : this.role.hashCode()));
    }
}
