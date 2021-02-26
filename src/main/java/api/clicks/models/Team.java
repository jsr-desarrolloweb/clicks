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
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int clicks;

    //N:M -> player
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
}
