package api.clicks.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int clicks;
    private String avatar;

    //N:M -> player
    @ToString.Exclude
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "teams", cascade = CascadeType.ALL)
    private Set<Player> players = new HashSet<>();


    public Team(){

    }

    public Team(String name, int clicks) {
        this.name = name;
        this.clicks = clicks;
    }

    public Team(String name, int clicks, Set<Player> players) {
        this.name = name;
        this.clicks = clicks;
        this.players = players;
    }

    public Team(String name, int clicks, String avatar, Set<Player> players) {
        this.name = name;
        this.clicks = clicks;
        this.avatar = avatar;
        this.players = players;
    }
}
