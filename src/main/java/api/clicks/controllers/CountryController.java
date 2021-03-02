package api.clicks.controllers;


import api.clicks.models.Country;
import api.clicks.models.Team;
import api.clicks.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
public class CountryController {
    @Autowired
    private CountryRepository countryRepository;

    @GetMapping(value="/countries")
    public ResponseEntity<?> getAllCountries(){
        List<Country> allCountriesList = countryRepository.findAll();
        if (allCountriesList.isEmpty()){
            return ResponseEntity.noContent().build(); //204
        }else{
            return ResponseEntity.ok(allCountriesList);
        }
    }


    @GetMapping(value = "/country/{id}")
    public ResponseEntity<?> getCountryDetail(@PathVariable Long id){
        Country country = countryRepository.findById(id).orElse(null);
        if (country == null){
            return  ResponseEntity.notFound().build(); //404
        }else{
            return ResponseEntity.ok(new Object[]{country, country.getProvinces()} );
        }
    }

    @PostMapping(value = "/country")
    public ResponseEntity<?> addCountry(@RequestBody Country country) {
        Optional<Country> oldCountry = countryRepository.findByName(country.getName());
        if (oldCountry.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //409
        } else {
            countryRepository.save(country);
            return ResponseEntity.status(HttpStatus.CREATED).body(country);
        }
    }

    @PutMapping(value = "/country/{id}")
    public ResponseEntity<Object> countryUpdate(@PathVariable("id") Long id, @RequestBody Country country) throws EntityNotFoundException {
        Country updatedCountry = countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        updatedCountry.setName(country.getName());
        updatedCountry.setClicks(country.getClicks());
        countryRepository.save(updatedCountry);
        return ResponseEntity.ok(updatedCountry);
    }

    @DeleteMapping(value = "/country/{id}")
    public ResponseEntity<Object> countryDelete(@PathVariable("id") Long id) {
        Optional<Country> countryToDelete = countryRepository.findById(id);
        if (countryToDelete.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        countryRepository.deleteById(id);
        return new ResponseEntity<>("Country  Deleted, id: " + id, HttpStatus.OK);
    }



}
