package example.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Book extends SerialId {

    private String name;

    private int rating;

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @ManyToMany
    @JoinTable(name = "book_store_mapping",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "store_id"))
    private Set<Store> stores = new HashSet<>();


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_tag_mapping",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}
