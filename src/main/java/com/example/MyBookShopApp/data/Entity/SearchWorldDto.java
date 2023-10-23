package com.example.MyBookShopApp.data.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchWorldDto {
    private String example;

    public SearchWorldDto(String example) {
        this.example = example;
    }

    public SearchWorldDto() {
    }
}
