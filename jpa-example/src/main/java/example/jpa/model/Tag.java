package example.jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Tag extends SerialId {
    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Book> books = new HashSet<>();
}
