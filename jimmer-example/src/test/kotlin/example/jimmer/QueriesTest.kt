package example.jimmer

import example.jimmer.dto.BookView
import example.jimmer.model.*
import example.jimmer.model.join.BookAuthorNameJoin
import org.assertj.core.api.Assertions.assertThat
import org.babyfish.jimmer.sql.ast.tuple.Tuple2
import org.babyfish.jimmer.sql.ast.tuple.Tuple3
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.ge
import org.junit.jupiter.api.Test

class QueriesTest : TestSupport() {
    @Test
    fun tupleQuery() {
        val books = sql.createQuery(Book::class) {
            where(
                table.rating ge 7,
                table.author.name eq "Martin Fowler",
                table.stores { name eq "Amazon" }
            )
            select(table.id, table.name)
        }.execute()

        assertThat(books).containsExactlyInAnyOrder(
            Tuple2(3, "Patterns of Enterprise Application Architecture")
        )
    }

    @Test
    fun viewQuery() {
        val books = sql.createQuery(Book::class) {
            where(
                table.rating ge 7,
                table.author.name eq "Martin Fowler",
                table.stores { name eq "Amazon" },
            )
            select(table.fetch(BookView::class))
        }.execute()

        assertThat(books).containsExactlyInAnyOrder(
            BookView("Patterns of Enterprise Application Architecture", 9)
        )
    }

    @Test
    fun customJoin() {
        val tuples = sql.createQuery(Book::class) {
            val author = table.asTableEx()
                .weakJoin(BookAuthorNameJoin::class)

            select(
                table.id,
                table.name,
                author.name,
            )
        }.execute()

        assertThat(tuples).containsExactlyInAnyOrder(
            Tuple3(5, "Bob Martin autobiography", "Bob Martin")
        )
    }

    @Test
    fun storeWithAuthor() {
        sql.createQuery(Book::class) {
            select(table, table.author)
        }.execute()
    }
}