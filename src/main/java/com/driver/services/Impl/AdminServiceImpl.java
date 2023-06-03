package com.driver.services.Impl;

import com.driver.model.Admin;
import com.driver.model.Country;
import com.driver.model.CountryName;
import com.driver.model.ServiceProvider;
import com.driver.repository.AdminRepository;
import com.driver.repository.CountryRepository;
import com.driver.repository.ServiceProviderRepository;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminRepository adminRepository1;

    @Autowired
    ServiceProviderRepository serviceProviderRepository1;

    @Autowired
    CountryRepository countryRepository1;

    @Override
    public Admin register(String username, String password) {

        Admin admin = new Admin();
        admin.setPassword(password);
        admin.setUsername(username);
        adminRepository1.save(admin);
        return admin;
    }

    @Override
    public Admin addServiceProvider(int adminId, String providerName) {
        Admin admin = adminRepository1.findById(adminId).get();
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName(providerName);
        serviceProvider.setAdmin(admin);

        admin.getServiceProviders().add(serviceProvider);
        adminRepository1.save(admin);
        return admin;


    }

    @Override
    public ServiceProvider addCountry(int serviceProviderId, String countryName) throws Exception{

        String countryname = countryName.toUpperCase();
        if (!countryname.equals("IND") && !countryname.equals("USA") && !countryname.equals("AUS") && !countryname.equals("CHI") && !countryname.equals("JPN")) throw new Exception("Country not found");
        ServiceProvider serviceProvider = serviceProviderRepository1.findById(serviceProviderId).get();

        Country country = new Country();
        country.setCountryName(CountryName.valueOf(countryname));
        country.setCode(CountryName.valueOf(countryname).toCode());
        country.setServiceProvider(serviceProvider);

        serviceProvider.getCountryList().add(country);
        serviceProviderRepository1.save(serviceProvider);
        return serviceProvider;
    }
}
