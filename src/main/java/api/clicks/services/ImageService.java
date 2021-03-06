package api.clicks.services;

import api.clicks.models.Player;
import api.clicks.models.Team;
import api.clicks.repositories.PlayerRepository;
import api.clicks.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;


    public boolean imageStore(MultipartFile file, Long id, String type) throws IOException {
        String myFileName = id.toString()+"-"+file.getOriginalFilename();
        Path targetPath = Paths.get("./images/"
                +myFileName)
                .normalize();
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
        if (type.equals("player")){
            Player player = playerRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException(id.toString()));
            player.setAvatar("/images/"+myFileName);
            playerRepository.save(player);
            return true;
        }
        if (type.equals("team")){
            Team team = teamRepository.findById(id)
                    .orElseThrow(()-> new EntityNotFoundException(id.toString()));
            team.setAvatar("/images/"+myFileName);
            teamRepository.save(team);
            return true;
        }
        return false;


    }
}
