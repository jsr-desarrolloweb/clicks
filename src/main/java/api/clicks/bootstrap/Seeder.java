package api.clicks.bootstrap;

import api.clicks.models.Locality;
import api.clicks.models.Player;
import api.clicks.models.Team;
import api.clicks.repositories.LocalityRepository;
import api.clicks.repositories.PlayerRepository;
import api.clicks.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Seeder implements CommandLineRunner {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private LocalityRepository localityRepository;

    @Override
    public void run(String[] args) {
        Team t1 = new Team("Los locos", 200);
        Team t2 = new Team("Las marihuanas", 234);
        teamRepository.save(t1);
        teamRepository.save(t2);

        //Set de equipos para los jugadores
        Set<Team> arrayTeams = new HashSet<>();
        arrayTeams.add(t1);
        arrayTeams.add(t2);

        Locality l1 = new Locality("Cadiz", 0 );
        Locality l2 = new Locality("Malaga", 2);
        localityRepository.save(l1);
        localityRepository.save(l2);

        Player p1 = new Player("jaime", "pestillo", null, 10, arrayTeams, l1);
        Player p2 = new Player("cristina", "pestillo", null, 8, null, l2);
        playerRepository.save(p1);
        playerRepository.save(p2);







    }
}
