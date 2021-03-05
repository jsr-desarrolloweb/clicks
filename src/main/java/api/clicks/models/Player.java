package api.clicks.models;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


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

    @Column(length = 300)
    private String token;

    //M:N -> Team
    @ManyToMany
    @ToString.Exclude
    private Set<Team> teams = new HashSet<>();

    //M:1 -> Locality
    @ManyToOne
    @ToString.Exclude
    @JoinColumn()
    private Locality locality;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn()
    private Role role;

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

    public Player(String name, String passwd,Role role, String avatar, int clicks, String token, Set<Team> teams, Locality locality) {
        this.name = name;
        this.passwd = new BCryptPasswordEncoder().encode(passwd);
        this.role = role;
        this.avatar = avatar;
        this.clicks = clicks;
        this.token = token;
        this.teams = teams;
        this.locality = locality;
    }
}
