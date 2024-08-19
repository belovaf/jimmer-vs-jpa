package example.jimmer.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.ManyToOne

@Entity
interface Book : SerialId {
    val name: String

    val rating: Int

    @ManyToOne
    val author: Author

    @ManyToMany
    val stores: List<Store>

    @ManyToMany
    val tags: List<Tag>
}