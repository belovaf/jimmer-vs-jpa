package example.jpa

import example.jpa.model.Book
import example.jpa.model.Store
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.Session
import org.junit.jupiter.api.Test

class ManyToManyTest : TestSupport() {
    @Test
    fun addBookToStoreIgnoreDuplicatesIncorrect() {
        val storeId = 2
        val bookIds = listOf(1, 2, 4)

        val store = em.find(Store::class.java, storeId)
        val bookRefs = bookIds.map { em.getReference(Book::class.java, it) }
        store.books.addAll(bookRefs)

        em.flush()
        em.clear()

        val actualStore = em.find(Store::class.java, storeId)
        assertThat(actualStore.books.map { it.id }).containsExactlyInAnyOrder(2, 3)
    }

    @Test
    fun addBookToStoreIgnoreDuplicatesCorrect() {
        val storeId = 2
        val bookIds = listOf(1, 2, 4)

        val books = em.unwrap(Session::class.java)
            .byMultipleIds(Book::class.java)
            .multiLoad(bookIds)

        val storeRef = em.getReference(Store::class.java, storeId)
        books.forEach { it.stores.add(storeRef) }

        em.flush()
        em.clear()

        val actualStore = em.find(Store::class.java, storeId)
        assertThat(actualStore.books.map { it.id }).containsExactlyInAnyOrder(1, 2, 3, 4)
    }
}