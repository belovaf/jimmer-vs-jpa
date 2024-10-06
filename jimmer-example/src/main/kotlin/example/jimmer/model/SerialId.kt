package example.jimmer.model

import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType.IDENTITY
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface SerialId {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long
}