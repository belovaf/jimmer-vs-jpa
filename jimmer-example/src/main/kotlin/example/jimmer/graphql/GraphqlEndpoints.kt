package example.jimmer.graphql

import example.jimmer.dto.BookSpec
import example.jimmer.model.Book
import example.jimmer.model.Store
import io.leangen.graphql.annotations.GraphQLArgument
import io.leangen.graphql.annotations.GraphQLEnvironment
import io.leangen.graphql.annotations.GraphQLQuery
import io.leangen.graphql.execution.ResolutionEnvironment
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi
import org.babyfish.jimmer.spring.graphql.toFetcher
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.stereotype.Component

@Component
@GraphQLApi
class GraphqlEndpoints(private val sql: KSqlClient) {

    @GraphQLQuery
    fun findStoreById(
        @GraphQLArgument(name = "id") id: Long,
        @GraphQLEnvironment env: ResolutionEnvironment,
    ): Store? = sql.findById(env.dataFetchingEnvironment.toFetcher(), id)

    @GraphQLQuery
    fun findBooks(
        @GraphQLArgument(name = "spec") spec: BookSpec,
        @GraphQLEnvironment env: ResolutionEnvironment,
    ): List<Book> = sql.createQuery(Book::class) {
        where(spec)
        select(table.fetch(env.dataFetchingEnvironment.toFetcher()))
    }.execute()
}