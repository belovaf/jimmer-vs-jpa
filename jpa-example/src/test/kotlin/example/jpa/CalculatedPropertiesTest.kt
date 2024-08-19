package example.jpa

import example.jpa.model.Author
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class CalculatedPropertiesTest : TestSupport() {
    @Test
    fun fetchAuthorRating() {
        val authors = em.createQuery("from Author", Author::class.java).resultList

        assertThat(authors.map { it.id to it.rating }).containsExactlyInAnyOrder(
            1L to 7.5,
            2L to 7.5,
            3L to 5.0,
        )
    }
}