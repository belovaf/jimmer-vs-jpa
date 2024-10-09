package example.jpa

import example.jpa.model.*
import jakarta.persistence.Tuple
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.function.Consumer

class QueriesTest : TestSupport() {
    @Test
    fun hqlTupleTest() {
        val query = """
            select id, name from Book book
             where rating >= :minRating
               and author.name = :authorName
               and exists(select 1 from Store store
                          join store.books b
                          where store.name = :storeName
                            and b.id = book.id
                         )
        """.trimIndent()

        val books = em.createQuery(query, Tuple::class.java)
            .setParameter("minRating", 7)
            .setParameter("authorName", "Martin Fowler")
            .setParameter("storeName", "Amazon")
            .resultList

        assertThat(books).satisfiesExactly(Consumer {
            assertThat(it.toArray()).containsExactlyInAnyOrder(3L, "Patterns of Enterprise Application Architecture")
        })
    }

    @Test
    fun criteriaTupleTest() {
        val cb = em.criteriaBuilder

        val minRating = cb.parameter(Integer::class.java, "minRating")
        val authorName = cb.parameter(String::class.java, "authorName")
        val storeName = cb.parameter(String::class.java, "storeName")

        val query = cb.createQuery(Tuple::class.java)
        val book = query.from(Book::class.java)
        val author = book.get(Book_.author)

        val subquery = query.subquery(Int::class.java)
        val store = subquery.from(Store::class.java)
        val b = store.join(Store_.books)

        subquery
            .select(cb.literal(1))
            .where(
                cb.equal(store.get(Store_.name), storeName),
                cb.equal(b.get(Book_.id), book.get(Book_.id))
            )

        query
            .multiselect(book.get(Book_.id), book.get(Book_.name))
            .where(
                cb.ge(book.get(Book_.rating), minRating),
                cb.equal(author.get(Author_.name), authorName),
                cb.exists(subquery)
            )

        val books = em.createQuery(query)
            .setParameter("minRating", 7)
            .setParameter("authorName", "Martin Fowler")
            .setParameter("storeName", "Amazon")
            .resultList

        assertThat(books).satisfiesExactly(Consumer {
            assertThat(it.toArray()).containsExactlyInAnyOrder(3L, "Patterns of Enterprise Application Architecture")
        })
    }
}