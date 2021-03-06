package api.clicks;

import api.clicks.models.Player;
import api.clicks.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClicksApplicationTests {

    @Autowired
    private PlayerRepository playerRepository;


    @BeforeEach
    void arrangeTest(){
        //preparación de datos, alta de datos...
        //Si testeo el borrado de una entidad, preparo aqui esa identidad
        //Siempre ANTES DE cada test
    }

    @Test
    void contextLoads() throws Exception {
        assert(playerRepository) != null;
    }


    @Test
    void insertPlayer() {
        playerRepository.save(new Player("pepe", "pestillo", null,0));
        assert playerRepository.findByName("pepe").isPresent();

        //Anotaciones para hacer tests con paquetes http

    }

    //Algun test de algun recurso/operacion

}
