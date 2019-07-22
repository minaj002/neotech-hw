package com.neotech.service;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CountryCode {

    private String country;
    private String code;

    public boolean equals(CountryCode another) {
        return country.equals(another.getCountry()) && code.equals(another.code);
    }

}
