package example.jpa

import example.jpa.model.Book_
import example.jpa.model.Store_
import example.jpa.model.Tag_
import example.jpa.model.Book
import example.jpa.model.Store
import example.jpa.model.Tag
import org.assertj.core.api.Assertions.assertThat
import org.hibernate.Hibernate
import org.hibernate.Session
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class WorkingWithGraphTest : TestSupport() {

    @Test
    fun fetchGraph() {
        val session = em.unwrap(Session::class.java)

        // Для basic столбцов работает только с bytecode enhancement и @Basic(fetch = FetchType.LAZY)
        val graph = session.createEntityGraph(Store::class.java).apply {
            addAttributeNode<String>(Store_.NAME)
            addAttributeNode<String>(Store_.WEBSITE)
            addAttributeNode<String>(Store_.VERSION)
            addSubgraph<Book>(Store_.BOOKS).apply {
                addAttributeNode<String>(Book_.NAME)
                addSubgraph<Tag>(Book_.TAGS).apply {
                    addAttributeNodes(Tag_.NAME)
                }
            }
        }
        val store = session.byId(Store::class.java)
            .withFetchGraph(graph)
            .load(1)

        assertThat(store).isNotNull
    }

    @Test
    fun fetchLazyLoading() {
        val store = em.find(Store::class.java, 1)
        Hibernate.initialize(store.books) // загружаем books
        store.books.forEach { Hibernate.initialize(it.tags) } // загружаем tags
    }

    @Test
    @Disabled
    fun replaceTags() {
        val tagNames = setOf("IT", "programming")

        val book = em.find(Book::class.java, 3)

        // удаляем тэги, которых нет во входящем списке
        book.tags.removeIf { it.name !in tagNames }

        // добавляем тэги, которые есть во входящем списке, но нет в текущем
        val newTags = tagNames - book.tags.mapTo(HashSet()) { it.name }
        book.tags.addAll(newTags.map(::Tag))

        em.flush()
        em.clear()

        val actualBook = em.find(Book::class.java, 3)
        assertThat(actualBook.tags.map { it.name }).containsExactlyInAnyOrderElementsOf(tagNames)
    }
}