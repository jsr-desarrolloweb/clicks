package api.clicks.controllers;



import api.clicks.models.Player;
import api.clicks.models.Province;
import api.clicks.models.Team;
import api.clicks.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;


@RestController
public class ProvinceController {
    @Autowired
    private ProvinceRepository provinceRepository;

    @GetMapping(value = "/provinces")
    public ResponseEntity<?> getAllProvinces(){
        List<Province> allProvincesList = provinceRepository.findAll();
        if (allProvincesList.isEmpty()){
            return ResponseEntity.noContent().build(); //204
        }else{
            return ResponseEntity.ok(allProvincesList);
        }
    }

    @GetMapping(value = "province/{id}")
    public ResponseEntity<?> getProvinceDetail(@PathVariable Long id){
        Province province = provinceRepository.findById(id).orElse(null);
        if (province == null){
            return  ResponseEntity.notFound().build(); //404
        }else{
            return ResponseEntity.ok( new Object[]{province, province.getLocalities()} );
        }
    }

    @PostMapping(value = "/province")
    public ResponseEntity<?> addProvince(@RequestBody Province province) {
        Optional<Province> oldProvince = provinceRepository.findByName(province.getName());
        if (oldProvince.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //409
        } else {
            provinceRepository.save(province);
            return ResponseEntity.status(HttpStatus.CREATED).body(province);
        }
    }

    @PutMapping(value = "/province/{id}")
    public ResponseEntity<Object> provinceUpdate(@PathVariable("id") Long id, @RequestBody Province province) throws EntityNotFoundException {
        Province updatedProvince = provinceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        updatedProvince.setName(province.getName());
        updatedProvince.setClicks(province.getClicks());
        provinceRepository.save(updatedProvince);
        return ResponseEntity.ok(updatedProvince);
    }

    @DeleteMapping(value = "/province/{id}")
    public ResponseEntity<Object> provinceDelete(@PathVariable("id") Long id) {
        Optional<Province> provinceToDelete = provinceRepository.findById(id);
        if (provinceToDelete.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        provinceRepository.deleteById(id);
        return new ResponseEntity<>("Province  Deleted, id: " + id, HttpStatus.OK);
    }

    @GetMapping(value = "/provinces/rating")
    public ResponseEntity<Object> getProvincesRating() {
        List<Province> result = provinceRepository.findAllByOrderByClicksDesc();
        return ResponseEntity.ok(result);
    }



}
