package example.jimmer

import example.jimmer.dto.AuthorBestBookView
import example.jimmer.dto.AuthorRatingView
import example.jimmer.model.Author
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CalculatedPropertiesTest : TestSupport() {
    @Test
    fun fetchAuthorRating() {
        val authors = sql.createQuery(Author::class) {
            select(table.fetch(AuthorRatingView::class))
        }.execute()

        assertThat(authors).containsExactlyInAnyOrder(
            AuthorRatingView(1, 7.5),
            AuthorRatingView(2, 7.5),
            AuthorRatingView(3, 5.0),
        )
    }

    @Test
    fun fetchAuthorBestBook() {
        val authors = sql.createQuery(Author::class) {
            select(table.fetch(AuthorBestBookView::class))
        }.execute()

        assertThat(authors).containsExactlyInAnyOrder(
            AuthorBestBookView(
                1,
                AuthorBestBookView.TargetOf_bestBook(
                    "Clean Code",
                    8
                )
            ),
            AuthorBestBookView(
                2,
                AuthorBestBookView.TargetOf_bestBook(
                    "Patterns of Enterprise Application Architecture",
                    9
                )
            ),
            AuthorBestBookView(
                3,
                AuthorBestBookView.TargetOf_bestBook(
                    "500 recipes",
                    5
                )
            )
        )
    }
}