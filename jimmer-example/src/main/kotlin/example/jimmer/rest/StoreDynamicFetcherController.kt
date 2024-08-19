package example.jimmer.rest

import example.jimmer.model.Store
import example.jimmer.model.by
import org.babyfish.jimmer.client.FetchBy
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dynamic")
class StoreDynamicFetcherController(private val sql: KSqlClient) {

    @GetMapping("/store/{id}")
    fun getStore(@PathVariable id: Long): @FetchBy("COMPLEX_FETCHER") Store? =
        sql.findById(COMPLEX_FETCHER, id)


    companion object {
        private val COMPLEX_FETCHER = newFetcher(Store::class).by {
            allScalarFields()
            books() {
                name()
                tags {
                    name()
                }
            }
        }
    }
}
