package com.example.quizmillionaire.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Language implements Serializable {
    private String name;
    private String abbreviation;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Language language = (Language) obj;
        return this.name != null && this.name.equals(language.name) && this.abbreviation != null &&
                this.abbreviation.equals(language.abbreviation);
    }

    @Override
    public int hashCode() {
        return 31 * ((null == this.name) ? 0 : this.name.hashCode()) +
                31 * ((null == this.abbreviation) ? 0 : this.abbreviation.hashCode());
    }
}
