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

        Country espania = new Country("España", 0);
        countryRepository.save(espania);

        Province cadiz = new Province("Cadiz", 0, espania);
        provinceRepository.save(cadiz);

        Locality medina = new Locality("Medina", 0, cadiz);
        localityRepository.save(medina);

        Team losLocos = new Team("Los Locos", 0);
        teamRepository.save(losLocos);
        Set<Team> equiposJugador = new HashSet<>();
        equiposJugador.add(losLocos);

        Player jaime = new Player("Jaime", "pestillo", null, 0, equiposJugador, medina);
        playerRepository.save(jaime);


    }
}
