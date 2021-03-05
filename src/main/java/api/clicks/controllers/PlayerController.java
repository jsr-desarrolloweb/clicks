package api.clicks.controllers;


import api.clicks.models.*;
import api.clicks.repositories.PlayerRepository;
import api.clicks.services.ImageService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
public class PlayerController {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private ImageService imageService;


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestParam("name") String name,
                                        @RequestParam("password") String password){


        String token = null;

        //compruebo name y passwdo
        Player player = playerRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(name));
        

        if(player.getToken() != null) {
            try {
                Jwts.parser().parse(player.getToken()).getBody();
                return new ResponseEntity<>("", HttpStatus.CONFLICT);
            } catch (Exception e) {
                player.setToken(null);
            }
        }
         //Generate token
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String secretKey = "pestillo";
        // TODO: Investigar todos los parámetros
        token = Jwts
                .builder()
                .setId("AlbertIES")
                .setSubject(name)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 6000000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        player.setToken(token);
        playerRepository.save(player);
        return new ResponseEntity<>(token, HttpStatus.OK);

    }



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


    @PostMapping(value = "player/avatar")
    public ResponseEntity<Object> setPlayerAvatar(@RequestParam("id") Long id, @RequestParam("file") MultipartFile file){
        playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        try {
            imageService.imageStore(file, id, "player");
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot set player avatar", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Player with id " +id+ " Avatar updated", HttpStatus.OK);
    }

    @GetMapping(value = "player/{id}/avatar")
    public ResponseEntity<Resource> getPlayerAvatar(@PathVariable("id") Long id){
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        try {
            Path targetPath = Paths.get("./"+player.getAvatar()).normalize();
            Resource resource = new UrlResource(targetPath.toUri());
            if (resource.exists()) {
                String contentType = Files.probeContentType(targetPath);
                return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
            }else{
                throw new EntityNotFoundException(id.toString());
            }
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }


    @PutMapping(value = "player/{id}/clicks")
    public ResponseEntity<Object> UpdatePlayerClicks(@PathVariable("id") Long id) throws IOException{
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        player.setClicks(player.getClicks() + 1);

        // TODO: refactor a un método del jugador
        //Si el jugador pertenece a algun equipo
        if (!player.getTeams().isEmpty()){
            for (Team team:
                 player.getTeams()) {
                    team.setClicks(team.getClicks()+1);
            }

            Locality playerLocality = player.getLocality();
            playerLocality.setClicks(playerLocality.getClicks() + 1);

            Province playerProvince = playerLocality.getProvince();
            playerProvince.setClicks(playerProvince.getClicks() + 1);

            Country playercountry = playerProvince.getCountry();
            playercountry.setClicks(playercountry.getClicks() + 1);

            playerRepository.save(player);
            return new ResponseEntity<>(id+" - "+player.getClicks(), HttpStatus.OK);
        }

        return new ResponseEntity<>("The player does not belong to a team", HttpStatus.CONFLICT);

    }




}
