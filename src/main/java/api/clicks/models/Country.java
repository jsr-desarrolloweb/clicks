package api.clicks.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int clicks;

    @EqualsAndHashCode.Exclude
    @JsonBackReference
    @OneToMany(mappedBy = "country")
    private Set<Province> provinces = new HashSet<>();

    public Country() {
    }

    public Country(String name, int clicks) {
        this.name = name;
        this.clicks = clicks;
    }


}
