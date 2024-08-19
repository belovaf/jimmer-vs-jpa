package example.jimmer.model.resolver

import example.jimmer.model.Book
import example.jimmer.model.authorId
import example.jimmer.model.id
import example.jimmer.model.rating
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.KTransientResolver
import org.babyfish.jimmer.sql.kt.ast.expression.*
import org.springframework.stereotype.Component

@Component
class AuthorBestBookResolver(private val sql: KSqlClient) : KTransientResolver<Long, Long> {
    override fun resolve(ids: Collection<Long>): Map<Long, Long> =
        sql.createQuery(Book::class) {
            where(table.authorId valueIn ids)
            where(notExists(subQuery(Book::class) {
                where(table.authorId eq parentTable.authorId)
                where(table.rating gt parentTable.rating)
                select(constant(1))
            }))
            groupBy(table.authorId)
            select(table.authorId, max(table.id).asNonNull())
        }.execute().associateBy({ it._1 }, { it._2 })
}