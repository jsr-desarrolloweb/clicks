package api.clicks.repositories;

import api.clicks.models.Locality;
import api.clicks.models.Player;
import api.clicks.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocalityRepository extends JpaRepository<Locality, Long> {
    Optional<Locality> findByName(String name);
    List<Locality> findAllByOrderByClicksDesc();
}
