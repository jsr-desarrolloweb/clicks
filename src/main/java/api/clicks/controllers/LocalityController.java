package api.clicks.controllers;

import api.clicks.models.Locality;
import api.clicks.repositories.LocalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
public class LocalityController {
    @Autowired
    private LocalityRepository localityRepository;

    @GetMapping(value="/localities")
    public ResponseEntity<?> getAllLocalities(){
        List<Locality> allLocalitiesList = localityRepository.findAll();
        if (allLocalitiesList.isEmpty()){
            return ResponseEntity.noContent().build(); //204
        }else{
            return ResponseEntity.ok(allLocalitiesList);
        }
    }

    @GetMapping(value = "locality/{id}")
    public ResponseEntity<?> getLocalityDetail(@PathVariable Long id){
        Locality locality = localityRepository.findById(id).orElse(null);
        if (locality == null){
            return  ResponseEntity.notFound().build(); //404
        }else{
            return ResponseEntity.ok(new Object[]{locality, locality.getPlayers()} );
        }
    }

    @PostMapping(value = "/locality")
    public ResponseEntity<?> addLocality(@RequestBody Locality locality) {
        Optional<Locality> oldLocality = localityRepository.findByName(locality.getName());
        if (oldLocality.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //409
        } else {
            localityRepository.save(locality);
            return ResponseEntity.status(HttpStatus.CREATED).body(locality);
        }
    }

    @PutMapping(value = "/locality/{id}")
    public ResponseEntity<Object> localityUpdate(@PathVariable("id") Long id, @RequestBody Locality locality) throws EntityNotFoundException {
        Locality updatedLocality = localityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        updatedLocality.setName(locality.getName());
        updatedLocality.setClicks(locality.getClicks());
        localityRepository.save(updatedLocality);
        return ResponseEntity.ok(updatedLocality);

    }

    @DeleteMapping(value = "/locality/{id}")
    public ResponseEntity<Object> localityDelete(@PathVariable("id") Long id) {
        Optional<Locality> localityToDelete = localityRepository.findById(id);
        if (localityToDelete.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        localityRepository.deleteById(id);
        return new ResponseEntity<>("Locality  Deleted, id: " + id, HttpStatus.OK);
    }

}
