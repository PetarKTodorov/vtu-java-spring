package com.example.taxi.models.binding;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

public class TaxiBM {
    private Long id;
    private String companyName;
    private String phones;
    private String webSiteLink;
    private double callPrice;
    private double initialFee;
    private double dailyRatePerKilometer;
    private double nightRatePerKilometer;
    private Date lastUpdated;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    @NotEmpty
    @Size(min=2, max=255)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @NotEmpty
    @Size(min=8, max=255)
    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getWebSiteLink() {
        return webSiteLink;
    }

    public void setWebSiteLink(String webSiteLink) {
        this.webSiteLink = webSiteLink;
    }
    
    @Range(min=1,max=100)
    public double getCallPrice() {
        return callPrice;
    }

    public void setCallPrice(double callPrice) {
        this.callPrice = callPrice;
    }

    @Range(min=1,max=100)
    public double getInitialFee() {
        return initialFee;
    }

    public void setInitialFee(double initialFee) {
        this.initialFee = initialFee;
    }

    @Range(min=1,max=100)
    public double getDailyRatePerKilometer() {
        return dailyRatePerKilometer;
    }

    public void setDailyRatePerKilometer(double dailyRatePerKilometer) {
        this.dailyRatePerKilometer = dailyRatePerKilometer;
    }

    @Range(min=1,max=100)
    public double getNightRatePerKilometer() {
        return nightRatePerKilometer;
    }

    public void setNightRatePerKilometer(double nightRatePerKilometer) {
        this.nightRatePerKilometer = nightRatePerKilometer;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
