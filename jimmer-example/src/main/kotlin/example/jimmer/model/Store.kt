package example.jimmer.model

import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.ManyToMany
import org.babyfish.jimmer.sql.Version

@Entity
interface Store : SerialId {

    @Version
    val version: Int

    val name: String

    val website: String?

    @ManyToMany(mappedBy = "stores")
    val books: List<Book>
}
