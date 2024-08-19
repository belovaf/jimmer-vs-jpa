package example.jpa.model;

import example.jpa.model.listener.AuthorRatingCalculatingListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@EntityListeners(AuthorRatingCalculatingListener.class)
public class Author extends SerialId {
    private String name;

    @Transient
    private Double rating;

    @OneToMany(mappedBy = "author")
    private Set<Book> books = new HashSet<>();
}
