package api.clicks.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
    @OneToMany(mappedBy = "province")
    private Set<Locality> localities = new HashSet<>();

    public Province() {
    }

    public Province(String name, int clicks) {
        this.name = name;
        this.clicks = clicks;
    }

    public Province(String name, int clicks, Set<Locality> localities) {
        this.name = name;
        this.clicks = clicks;
        this.localities = localities;
    }
}
