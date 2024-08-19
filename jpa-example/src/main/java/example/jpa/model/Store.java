package example.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Store extends SerialId {

    @Version
    private int version;

    @Basic(fetch = FetchType.LAZY)
    private String name;

    @Basic(fetch = FetchType.LAZY)
    private String website;

    @ManyToMany(mappedBy = "stores")
    private Set<Book> books = new LinkedHashSet<>();
}
