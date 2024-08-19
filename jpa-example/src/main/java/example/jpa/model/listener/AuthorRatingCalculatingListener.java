package example.jpa.model.listener;

import example.jpa.model.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PostLoad;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorRatingCalculatingListener {
    final ObjectFactory<EntityManagerFactory> emf;

    @PostLoad
    void afterLoad(Author author) {
        try (EntityManager em = emf.getObject().createEntityManager()) {
            double avgBooksRating = em.createQuery("select avg(rating) from Book where author = ?1", Double.class)
                    .setParameter(1, author)
                    .getSingleResult();
            author.setRating(avgBooksRating);
        }
    }
}
