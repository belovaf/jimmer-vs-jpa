package example.jimmer.rest

import example.jimmer.dto.BookInput
import example.jimmer.dto.BookSpec
import example.jimmer.dto.BookView
import example.jimmer.model.Book
import org.babyfish.jimmer.sql.ast.mutation.SaveMode
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.web.bind.annotation.*

@RestController
class BookController(private val sql: KSqlClient) {

    @GetMapping("/books")
    fun findBooks(spec: BookSpec): List<BookView> =
        sql.createQuery(Book::class) {
            where(spec)
            select(table.fetch(BookView::class))
        }.execute()

    @PutMapping("/books/{id}")
    fun updateBook(@PathVariable id: Long, input: BookInput) {
        sql.update(input.toEntity { this.id = id })
    }

    @PostMapping("/books")
    fun createBook(input: BookInput) {
        sql.merge(input, SaveMode.INSERT_ONLY)
    }
}