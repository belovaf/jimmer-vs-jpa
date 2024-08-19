package example.jimmer.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.KeyUniqueConstraint
import org.babyfish.jimmer.sql.ManyToMany

@Entity
@KeyUniqueConstraint
interface Tag : SerialId {
    @Key
    val name: String

    @ManyToMany(mappedBy = "tags")
    val books: List<Book>

}

fun Tag(name: String): Tag = Tag { this.name = name }
