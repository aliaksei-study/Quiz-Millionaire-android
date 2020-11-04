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

    public Player(String username) {
        this.username = username;
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
        return this.username != null && this.username.equals(player.username);
    }

    @Override
    public int hashCode() {
        return (31 * ((this.username == null) ? 0 : this.username.hashCode()));
    }
}
