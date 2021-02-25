package api.clicks.models;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String passwd;
    private String avatar;
    private int clicks;

    public Player(){

    }

    public Player(String name, String passwd, String avatar, int clicks) {
        this.name = name;
        this.passwd = passwd;
        this.avatar = avatar;
        this.clicks = clicks;
    }
}
