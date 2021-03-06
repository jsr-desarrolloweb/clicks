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
        Country inglaterra = new Country("Inglaterra", 0);
        countryRepository.save(inglaterra);

        Province cadiz = new Province("Cadiz", 0, espania);
        provinceRepository.save(cadiz);
        Province chelsea = new Province("Chelsea", 0, inglaterra);
        provinceRepository.save(chelsea);

        Locality medina = new Locality("Medina", 0, cadiz);
        localityRepository.save(medina);
        Locality bourn = new Locality("Bourn", 0, chelsea);
        localityRepository.save(bourn);

        Team losLocos = new Team("Los Locos", 0, null, null);
        teamRepository.save(losLocos);
        Team lasAmigas = new Team("Las Amigas", 0, null, null);
        teamRepository.save(lasAmigas);

        Set<Team> equiposJugador1 = new HashSet<>();
        equiposJugador1.add(losLocos);
        Set<Team> equiposJugador2 = new HashSet<>();
        equiposJugador2.add(lasAmigas);


        Player jaime = new Player("jaime", "pestillo", admin, null, 0,null, equiposJugador1, medina);
        Player cristina = new Player("cristina", "pestillo", guest, null, 0,null, equiposJugador2, bourn);
        playerRepository.save(jaime);
        playerRepository.save(cristina);


    }
}
