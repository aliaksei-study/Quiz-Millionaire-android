package com.example.quizmillionaire.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Category implements Serializable {
    private Long id;
    private String categoryName;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(null == obj || this.getClass() != obj.getClass()) {
            return false;
        }
        Category category = (Category) obj;
        if(null != this.categoryName) {
            return this.categoryName.equals(category.categoryName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return  31 * ((null == this.categoryName) ? 0 : this.categoryName.hashCode());
    }
}
