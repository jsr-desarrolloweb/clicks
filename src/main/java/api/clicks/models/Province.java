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
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int clicks;

    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @ToString.Exclude
    @OneToMany(mappedBy = "province")
    private Set<Locality> localities = new HashSet<>();

    @ManyToOne
    @ToString.Exclude
    @JoinColumn()
    private Country country;

    public Province() {
    }

    public Province(String name, int clicks) {
        this.name = name;
        this.clicks = clicks;
    }

    public Province(String name, int clicks, Country country) {
        this.name = name;
        this.clicks = clicks;
        this.country = country;
    }
}
