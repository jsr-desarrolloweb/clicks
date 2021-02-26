package api.clicks.models;



import lombok.Data;


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

    //M:1 -> Locality
    @ManyToOne
    @JoinColumn()
    private Locality locality;

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

    public Player(String name, String passwd, String avatar, int clicks, Set<Team> teams, Locality locality) {
        this.name = name;
        this.passwd = passwd;
        this.avatar = avatar;
        this.clicks = clicks;
        this.teams = teams;
        this.locality = locality;
    }
}
