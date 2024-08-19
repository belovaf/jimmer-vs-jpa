package example.jpa

import example.jpa.dto.StoreNameOnlyView
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ProjectionsTest : TestSupport() {
    @Test
    fun viewProjection() {
        val stores = em.createQuery("select new example.jpa.dto.StoreNameOnlyView(name) from Store").resultList
        assertThat(stores).containsExactlyInAnyOrder(
            StoreNameOnlyView("Amazon"),
            StoreNameOnlyView("Читай Город"),
        )
    }
}