package api.clicks.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    //M:N -> Team
    @ManyToMany
    private Set<Team> teams = new HashSet<>();

    public Player(){

    }

    public Player(String name, String passwd, String avatar, int clicks) {
        this.name = name;
        this.passwd = passwd;
        this.avatar = avatar;
        this.clicks = clicks;
    }

    public Player(String name, String passwd, String avatar, int clicks, Set<Team> teams) {
        this.name = name;
        this.passwd = passwd;
        this.avatar = avatar;
        this.clicks = clicks;
        this.teams = teams;
    }
}
