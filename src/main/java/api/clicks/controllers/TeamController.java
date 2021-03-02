package api.clicks.controllers;



import api.clicks.models.Team;

import api.clicks.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
public class TeamController {
    @Autowired
    private TeamRepository teamRepository;

    @GetMapping(value="/teams")
    public ResponseEntity<?> getAllTeams(){
        List<Team> allTeamsList = teamRepository.findAll();
        if (allTeamsList.isEmpty()){
            return ResponseEntity.noContent().build(); //204
        }else{
            return ResponseEntity.ok(allTeamsList);
        }
    }

    @GetMapping(value = "/team/{id}")
    public ResponseEntity<?> getTeamDetail(@PathVariable Long id){
        Team team = teamRepository.findById(id).orElse(null);
        if (team == null){
            return  ResponseEntity.notFound().build(); //404
        }else{
            return ResponseEntity.ok(new Object[]{team, team.getPlayers()}  );
        }
    }

    @PostMapping(value = "/team")
    public ResponseEntity<?> addTeam(@RequestBody Team team) {
        Optional<Team> oldTeam = teamRepository.findByName(team.getName());
        if (oldTeam.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //409
        } else {
            teamRepository.save(team);
            return ResponseEntity.status(HttpStatus.CREATED).body(team);
        }
    }

    @PutMapping(value = "/team/{id}")
    public ResponseEntity<Object> teamUpdate(@PathVariable("id") Long id, @RequestBody Team team) throws EntityNotFoundException {
        Team updatedTeam = teamRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        updatedTeam.setName(team.getName());
        updatedTeam.setClicks(team.getClicks());
        teamRepository.save(updatedTeam);
        return ResponseEntity.ok(updatedTeam);

    }

    @DeleteMapping(value = "/team/{id}")
    public ResponseEntity<Object> teamDelete(@PathVariable("id") Long id) {
        Optional<Team> teamToDelete = teamRepository.findById(id);
        if (teamToDelete.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        teamRepository.deleteById(id);
        return new ResponseEntity<>("Team  Deleted, id: " + id, HttpStatus.OK);
    }



}
