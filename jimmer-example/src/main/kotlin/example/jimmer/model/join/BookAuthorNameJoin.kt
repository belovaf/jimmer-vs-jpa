package example.jimmer.model.join

import example.jimmer.ext.like
import example.jimmer.model.Author
import example.jimmer.model.Book
import example.jimmer.model.name
import org.babyfish.jimmer.sql.kt.ast.expression.KNonNullExpression
import org.babyfish.jimmer.sql.kt.ast.table.KNonNullTable
import org.babyfish.jimmer.sql.kt.ast.table.KWeakJoin

class BookAuthorNameJoin : KWeakJoin<Book, Author>() {
    override fun on(
        source: KNonNullTable<Book>,
        target: KNonNullTable<Author>
    ): KNonNullExpression<Boolean> =
        source.name like target.name
}

