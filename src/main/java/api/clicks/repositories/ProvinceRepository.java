package api.clicks.repositories;

import api.clicks.models.Player;
import api.clicks.models.Province;
import api.clicks.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.plaf.synth.Region;
import java.util.List;
import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Province> findByName(String name);
    List<Province> findAllByOrderByClicksDesc();
}
