package api.clicks.controllers;


import api.clicks.models.Player;
import api.clicks.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping(value="/players")
    public ResponseEntity<?> getAllPlayers(){
        List<Player> allPlayersList = playerRepository.findAll();
        if (allPlayersList.isEmpty()){
            return ResponseEntity.noContent().build(); //204
        }else{
            return ResponseEntity.ok(allPlayersList);
        }
    }

    @GetMapping(value = "player/{id}")
    public ResponseEntity<?> getPlayerDetail(@PathVariable Long id){
        Player player = playerRepository.findById(id).orElse(null);
        if (player == null){
            return  ResponseEntity.notFound().build(); //404
        }else{
            return ResponseEntity.ok(player);
        }
    }

    @PostMapping(value = "/player")
    public ResponseEntity<?> addPlayer(@RequestBody Player player) {
        Optional<Player> oldPlayer = playerRepository.findByName(player.getName());
        if (oldPlayer.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //409
        } else {
            playerRepository.save(player);
            return ResponseEntity.status(HttpStatus.CREATED).body(player);
        }
    }

    @PutMapping(value = "/player/{id}")
    public ResponseEntity<Object> playerUpdate(@PathVariable("id") Long id, @RequestBody Player player) throws EntityNotFoundException {
        Player updatedPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        updatedPlayer.setName(player.getName());
        updatedPlayer.setPasswd(player.getPasswd());
        updatedPlayer.setAvatar(player.getAvatar());
        updatedPlayer.setClicks(player.getClicks());
        playerRepository.save(updatedPlayer);
        return ResponseEntity.ok(updatedPlayer);

    }

    @DeleteMapping(value = "/player/{id}")
    public ResponseEntity<Object> playerDelete(@PathVariable("id") Long id) {
        Optional<Player> playerToDelete = playerRepository.findById(id);
        if (playerToDelete.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        playerRepository.deleteById(id);
        return new ResponseEntity<>("Player  Deleted, id: " + id, HttpStatus.OK);
    }



}
