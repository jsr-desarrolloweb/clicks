package api.clicks.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Locality {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int clicks;

    //1:M -> players
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "locality")
    private Set<Player> players = new HashSet<>();

    @ManyToOne
    @JoinColumn()
    private Province province;

    public Locality() {
    }

    public Locality(String name, int clicks) {
        this.name = name;
        this.clicks = clicks;
    }

    public Locality(String name, int clicks, Set<Player> players) {
        this.name = name;
        this.clicks = clicks;
        this.players = players;
    }

    public Locality(String name, int clicks, Province province) {
        this.name = name;
        this.clicks = clicks;
        this.players = players;
        this.province = province;
    }
}
