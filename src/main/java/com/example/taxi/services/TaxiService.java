package com.example.taxi.services;

import com.example.taxi.models.binding.TaxiBM;
import com.example.taxi.models.entities.Taxi;
import com.example.taxi.models.entities.User;
import com.example.taxi.models.view.TaxiVM;
import com.example.taxi.models.view.UserVM;
import com.example.taxi.repositories.TaxiRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxiService {
    private final ModelMapper modelMapper;
    private final TaxiRepository taxiRepository;

    public TaxiService(ModelMapper modelMapper, TaxiRepository petRepository) {
        this.modelMapper = modelMapper;
        this.taxiRepository = petRepository;
    }

    public Taxi findById(long id) {
        Taxi taxi = taxiRepository.findById(id).orElse(null);

        return taxi;
    }

    public List<TaxiVM> findAllTaxisView() {
        return taxiRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, TaxiVM.class))
                .collect(Collectors.toList());
    }

    public List<TaxiVM> findSearchedTaxiView(String companyName) {
        if (companyName.isEmpty()) {
            throw new RuntimeException("Field cannot be empty!");
        }

        List<TaxiVM> texis = taxiRepository.searchByCompanyNameStartingWith(companyName)
                .orElse(null)
                .stream()
                .map(p -> modelMapper.map(p, TaxiVM.class))
                .collect(Collectors.toList());

        if (texis.size() == 0) {
            throw new RuntimeException("There are no taxi!");
        }

        return texis;
    }

    public void addTaxi(TaxiBM taxiBM, User user) {
        validateTaxiCompanyName(taxiBM.getCompanyName());

        Taxi newTaxi = modelMapper.map(taxiBM, Taxi.class);
        newTaxi.setLastUpdated(new Date());

        if (user == null) {
            throw new RuntimeException("User is not logged!");
        }

        taxiRepository.save(newTaxi);
    }

    public TaxiBM findTaxi(Long id) {
        Taxi taxi = taxiRepository.findById(id).orElse(null);

        validateTaxi(taxi);

        return modelMapper.map(taxi, TaxiBM.class);
    }

    public void updateTaxi(TaxiBM taxiBM, User user) {
        Taxi taxi = taxiRepository.findById(taxiBM.getId()).orElse(null);

        validateTaxi(taxi);

        if(!taxi.getCompanyName().equals(taxiBM.getCompanyName())) {
            validateTaxiCompanyName(taxiBM.getCompanyName());
        }

        taxi.setCompanyName(taxiBM.getCompanyName());
        taxi.setPhones(taxiBM.getPhones());
        taxi.setWebSiteLink(taxiBM.getWebSiteLink());
        taxi.setCallPrice(taxiBM.getCallPrice());
        taxi.setInitialFee(taxiBM.getInitialFee());
        taxi.setDailyRatePerKilometer(taxiBM.getDailyRatePerKilometer());
        taxi.setNightRatePerKilometer(taxiBM.getNightRatePerKilometer());
        taxi.setLastUpdated(new Date());

        taxiRepository.save(taxi);
    }

    public void deleteTaxi(Long id, User user) {
        Taxi taxi = taxiRepository.findById(id).orElse(null);

        validateTaxi(taxi);

        taxiRepository.deleteById(taxi.getId());
    }

    private void validateTaxi(Taxi taxi) {
        if (taxi == null) {
            throw new RuntimeException("Taxi not found!");
        }
    }

    private void validateTaxiCompanyName(String name) {
        if (taxiRepository.findByCompanyName(name).orElse(null) != null) {
            throw new RuntimeException("Taxi name is taken!");
        }
    }
}
