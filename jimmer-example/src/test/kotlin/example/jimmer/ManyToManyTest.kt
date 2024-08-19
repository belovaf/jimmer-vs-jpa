package example.jimmer

import example.jimmer.model.Book
import example.jimmer.model.Store
import example.jimmer.model.books
import example.jimmer.model.id
import org.assertj.core.api.Assertions.assertThat
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.junit.jupiter.api.Test

class ManyToManyTest : TestSupport() {
    @Test
    fun addBookToStore() {
        val storeIds = listOf(2L)
        val bookIds = listOf(1L, 4L)

        val count = sql.getAssociations(Book::stores).saveAll(bookIds, storeIds)

        assertThat(count).isEqualTo(2)
        assertThat(queryBookIdsFor(storeIds)).containsExactlyInAnyOrder(1L, 2L, 3L, 4L)
    }

    @Test
    fun addBooksToStoreIgnoreDuplicates() {
        val storeIds = listOf(2L)
        val bookIds = listOf(1L, 2L, 4L)

        val count = sql.getAssociations(Store::books).saveAll(storeIds, bookIds, checkExistence = true)

        assertThat(count).isEqualTo(2)
        assertThat(queryBookIdsFor(storeIds)).containsExactlyInAnyOrder(1L, 2L, 3L, 4L)
    }

    @Test
    fun addBooksToStoreIgnoreReversedDuplicates() {
        val storeIds = listOf(2L)
        val bookIds = listOf(1L, 4L)

        // Полностью эквивалентно
        val count = sql.getAssociations(Book::stores).saveAll(bookIds, storeIds, checkExistence = true)

        assertThat(count).isEqualTo(2)
        assertThat(queryBookIdsFor(storeIds)).containsExactlyInAnyOrder(1L, 2L, 3L, 4L)
    }

    private fun queryBookIdsFor(storeIds: List<Long>): List<Long> {
        val actualBookIds = sql.createQuery(Store::class) {
            where(table.id valueIn storeIds)
            select(table.asTableEx().books.id)
        }.execute()
        return actualBookIds
    }
}