package example.jimmer

import example.jimmer.dto.BookInput
import example.jimmer.dto.BookSpec
import example.jimmer.dto.BookView
import example.jimmer.dto.StoreNameOnlyView
import example.jimmer.model.Book
import example.jimmer.model.Store
import example.jimmer.model.by
import org.assertj.core.api.Assertions.assertThat
import org.babyfish.jimmer.UnloadedException
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

private val bookFetcher = newFetcher(Book::class).by {
    allTableFields()
    stores()
    tags {
        name()
    }
}

class ProjectionsTest : TestSupport() {

    @Test
    fun unloadedException() {
        val fetcher = newFetcher(Store::class).by {
            name()
        }
        val store = sql.findById(fetcher, 1)!!

        assertThrows<UnloadedException> { store.website } // ???
    }

    @Test
    fun fetchDto() {
        val store = sql.findById(StoreNameOnlyView::class, 1)!!
        assertThat(store.name).isEqualTo("Amazon")
        // we can't access store.website anymore
    }

    @Test
    fun createInput() {
        val input = BookInput(
            "new book",
            10,
            1,
            listOf(1, 2),
            listOf(
                BookInput.TargetOf_tags("IT"),
                BookInput.TargetOf_tags("new tag"),
            )
        )
        val book = sql.merge(input, SaveMode.INSERT_ONLY).modifiedEntity

        sql.findById(bookFetcher, book.id)!!.run {
            assertThat(name).isEqualTo("new book")
            assertThat(rating).isEqualTo(10)
            assertThat(author.id).isEqualTo(1)
            assertThat(stores.map { it.id }).containsExactlyInAnyOrder(1L, 2L)
            assertThat(tags.map { it.name }).containsExactlyInAnyOrder("IT", "new tag")
        }
    }

    @Test
    fun updateInput() {
        val input = BookInput(
            "new book",
            10,
            1,
            listOf(1, 2),
            listOf(
                BookInput.TargetOf_tags("IT"),
                BookInput.TargetOf_tags("new tag"),
            )
        )
        sql.save(input.toEntity { id = 1 }, SaveMode.UPDATE_ONLY)

        sql.findById(bookFetcher, 1)!!.run {
            assertThat(name).isEqualTo("new book")
            assertThat(rating).isEqualTo(10)
            assertThat(author.id).isEqualTo(1)
            assertThat(stores.map { it.id }).containsExactlyInAnyOrder(1L, 2L)
            assertThat(tags.map { it.name }).containsExactlyInAnyOrder("IT", "new tag")
        }

    }

    @Test
    fun specification() {
        val books = sql.createQuery(Book::class) {
            where(BookSpec(minRating = 5, tag = "IT"))
            select(table.fetch(BookView::class))
        }.execute()

        assertThat(books.map { it.name }).containsExactlyInAnyOrder(
            "Clean Code",
            "Refactoring",
            "Patterns of Enterprise Application Architecture",
        )
    }
}