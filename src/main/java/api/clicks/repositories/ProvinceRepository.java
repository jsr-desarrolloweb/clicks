package api.clicks.repositories;

import api.clicks.models.Player;
import api.clicks.models.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.plaf.synth.Region;
import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Optional<Province> findByName(String name);

}
