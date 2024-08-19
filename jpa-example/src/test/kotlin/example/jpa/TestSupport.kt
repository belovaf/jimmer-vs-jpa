package example.jpa

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
abstract class TestSupport {
    @PersistenceContext
    protected lateinit var em: EntityManager
}