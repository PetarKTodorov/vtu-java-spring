package com.example.taxi.models.binding;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SearchBM {
    private String companyName;


    @Size(min=0, max=255)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
