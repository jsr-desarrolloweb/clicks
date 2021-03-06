package api.clicks.repositories;

import api.clicks.models.Player;
import api.clicks.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long>  {
    Optional<Team> findByName(String name);
    List<Team> findAllByOrderByClicksDesc();

}
