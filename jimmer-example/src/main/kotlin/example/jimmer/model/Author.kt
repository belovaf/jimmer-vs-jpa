package example.jimmer.model

import example.jimmer.model.resolver.AuthorBestBookResolver
import example.jimmer.model.resolver.AuthorRatingResolver
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.OneToMany
import org.babyfish.jimmer.sql.Transient

@Entity
interface Author : SerialId {
    val name: String

    @Transient(AuthorRatingResolver::class)
    val rating: Double?

    @Transient(AuthorBestBookResolver::class)
    val bestBook: Book?

    @OneToMany(mappedBy = "author")
    val books: List<Book>
}