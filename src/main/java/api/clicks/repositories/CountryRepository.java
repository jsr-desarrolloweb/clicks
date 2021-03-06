package api.clicks.repositories;

import api.clicks.models.Country;
import api.clicks.models.Locality;
import api.clicks.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String name);
    List<Country> findAllByOrderByClicksDesc();
}
