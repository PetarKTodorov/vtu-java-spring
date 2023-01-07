package com.example.taxi.models.view;

import com.example.taxi.models.entities.User;
import com.example.taxi.repositories.TaxiRepository;
import org.modelmapper.ModelMapper;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TaxiVM {
    private Long id;
    private String companyName;
    private String phones;
    private String webSiteLink;
    private double callPrice;
    private double initialFee;
    private double dailyRatePerKilometer;
    private double nightRatePerKilometer;
    private Date lastUpdated;
    private Set<User> likedUsers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

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

    public double getCallPrice() {
        return callPrice;
    }

    public void setCallPrice(double callPrice) {
        this.callPrice = callPrice;
    }

    public double getInitialFee() {
        return initialFee;
    }

    public void setInitialFee(double initialFee) {
        this.initialFee = initialFee;
    }

    public double getDailyRatePerKilometer() {
        return dailyRatePerKilometer;
    }

    public void setDailyRatePerKilometer(double dailyRatePerKilometer) {
        this.dailyRatePerKilometer = dailyRatePerKilometer;
    }

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

    public Set<User> getLikedUsers() {
        return likedUsers;
    }

    public void setLikedUsers(Set<User> likedUsers) {
        this.likedUsers = likedUsers;
    }
}
