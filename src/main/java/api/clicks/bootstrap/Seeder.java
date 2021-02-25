package api.clicks.bootstrap;

import api.clicks.models.Player;
import api.clicks.models.Team;
import api.clicks.repositories.PlayerRepository;
import api.clicks.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Seeder implements CommandLineRunner {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Override
    public void run(String[] args) {
        Player p1 = new Player("jaime", "pestillo", null, 10);
        Player p2 = new Player("cristina", "pestillo", null, 8);
        playerRepository.save(p1);
        playerRepository.save(p2);

        Team t1 = new Team("Los locos", 200);
        Team t2 = new Team("Las marihuanas", 234);
        teamRepository.save(t1);
        teamRepository.save(t2);



    }
}
