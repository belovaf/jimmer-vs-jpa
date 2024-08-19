package example.jimmer.ext

import org.babyfish.jimmer.sql.kt.ast.expression.KExpression
import org.babyfish.jimmer.sql.kt.ast.expression.sql

infix fun KExpression<String>.like(target: KExpression<String>) =
    sql(Boolean::class, "%e like '%' || %e || '%'") {
        expression(this@like)
        expression(target)
    }