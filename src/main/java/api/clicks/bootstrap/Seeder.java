package api.clicks.bootstrap;

import api.clicks.models.*;
import api.clicks.repositories.*;
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
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public void run(String[] args) {
/*        //TEAMS
        Team t1 = new Team("Los locos", 200);
        Team t2 = new Team("Las marihuanas", 234);
        teamRepository.save(t1);
        teamRepository.save(t2);

        //Set de equipos para los jugadores
        Set<Team> arrayTeams = new HashSet<>();
        arrayTeams.add(t1);
        arrayTeams.add(t2);

        //PROVINCES
        Province prov1 = new Province("Cadiz", 0);
        Province prov2 = new Province("Sevilla", 0);
        provinceRepository.save(prov1);
        provinceRepository.save(prov2);


        //LOCALITIES
        Locality l1 = new Locality("Jerez", 0);
        Locality l2 = new Locality("Medina", 2);
        localityRepository.save(l1);
        localityRepository.save(l2);


        //PLAYERS
        Player p1 = new Player("jaime", "pestillo", null, 10, arrayTeams, l1);
        Player p2 = new Player("cristina", "pestillo", null, 8, null, l2);
        playerRepository.save(p1);
        playerRepository.save(p2);*/
        Province cadiz = new Province("Cadiz", 0);
        provinceRepository.save(cadiz);

        Locality medina = new Locality("Medina", 0, cadiz);
        localityRepository.save(medina);

        Team losLocos = new Team("Los Locos", 0);
        teamRepository.save(losLocos);
        Set<Team> equiposJugador = new HashSet<>();
        equiposJugador.add(losLocos);

        Player jaime = new Player("Jaime", "pestillo", null, 0, equiposJugador, medina);
        playerRepository.save(jaime);

        Country espania = new Country("España", 0);
        countryRepository.save(espania);
    }
}
