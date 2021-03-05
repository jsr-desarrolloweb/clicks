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
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String[] args) {

        Role admin = new Role("ROLE_ADMIN");
        Role guest = new Role("ROLE_GUEST");
        roleRepository.save(admin);
        roleRepository.save(guest);

        Country espania = new Country("España", 0);
        countryRepository.save(espania);

        Province cadiz = new Province("Cadiz", 0, espania);
        provinceRepository.save(cadiz);

        Locality medina = new Locality("Medina", 0, cadiz);
        localityRepository.save(medina);

        Team losLocos = new Team("Los Locos", 0, null, null);
        teamRepository.save(losLocos);
        Set<Team> equiposJugador = new HashSet<>();
        equiposJugador.add(losLocos);

        Player jaime = new Player("jaime", "pestillo", admin, null, 0,null, equiposJugador, medina);
        Player cristina = new Player("cristina", "pestillo", admin, null, 0,null, equiposJugador, medina);
        playerRepository.save(jaime);
        playerRepository.save(cristina);


    }
}
