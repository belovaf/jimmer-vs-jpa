package example.jimmer

import example.jimmer.model.Tag
import example.jimmer.model.*
import org.assertj.core.api.Assertions.assertThat
import org.babyfish.jimmer.sql.ast.mutation.AssociatedSaveMode
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.junit.jupiter.api.Test

class WorkingWithGraphTest : TestSupport() {

    @Test
    fun fetchGraph() {
        val fetcher = newFetcher(Store::class).by {
            allScalarFields()
            books {
                name()
                tags {
                    name()
                }
            }
        }
        val store = sql.findById(fetcher, 1)
        assertThat(store).isNotNull()
    }

    @Test
    fun replaceTags() {
        val tagNames = setOf("IT", "programming")

        sql.update(Book {
            id = 1
            tags = tagNames.map(::Tag)
        })

        val actualTagNames = sql.createQuery(Tag::class) {
            where(table.books { id eq 1 })
            select(table.name)
        }.execute()

        assertThat(actualTagNames).containsExactlyElementsOf(tagNames)
    }

    @Test
    fun updateStore() {
        sql.save(Store {
            id = 1
            version = 0
            books().addBy {
                name = "book"
                rating = 5
                authorId = 1
            }
        }) {
            setMode(SaveMode.UPDATE_ONLY)
            setAssociatedMode(Store::books, AssociatedSaveMode.VIOLENTLY_REPLACE)
        }

        val storeBookNames = sql.createQuery(Book::class) {
            where(table.stores { id eq 1 })
            select(table.name)
        }.execute()

        assertThat(storeBookNames).containsExactly("book")
    }
}