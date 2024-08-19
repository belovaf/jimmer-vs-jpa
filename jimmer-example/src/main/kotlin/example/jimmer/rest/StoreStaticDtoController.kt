package example.jimmer.rest

import example.jimmer.dto.StoreView
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/static")
class StoreStaticDtoController(private val sql: KSqlClient) {

    @GetMapping("/stores/{id}")
    fun findStoreById(@PathVariable id: Long): StoreView? =
        sql.findById(StoreView::class, id)

}
