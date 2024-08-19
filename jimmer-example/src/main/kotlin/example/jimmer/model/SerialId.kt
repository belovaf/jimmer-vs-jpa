package example.jimmer.model

import org.babyfish.jimmer.sql.GeneratedValue
import org.babyfish.jimmer.sql.GenerationType
import org.babyfish.jimmer.sql.Id
import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface SerialId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long
}