package example.jimmer.model.resolver

import example.jimmer.model.Book
import example.jimmer.model.authorId
import example.jimmer.model.rating
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.KTransientResolver
import org.babyfish.jimmer.sql.kt.ast.expression.avg
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.stereotype.Component

@Component
class AuthorRatingResolver(private val sql: KSqlClient) : KTransientResolver<Long, Double?> {
    override fun resolve(ids: Collection<Long>): Map<Long, Double?> =
        sql.createQuery(Book::class) {
            where(table.authorId valueIn ids)
            groupBy(table.authorId)
            select(table.authorId, avg(table.rating))
        }.execute().associateBy({ it._1 }, { it._2 })
}
