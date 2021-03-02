package api.clicks.repositories;

import api.clicks.models.Country;
import api.clicks.models.Locality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String name);
}
