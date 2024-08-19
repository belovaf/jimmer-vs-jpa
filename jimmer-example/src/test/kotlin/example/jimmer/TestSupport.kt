package example.jimmer

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
abstract class TestSupport {
    @Autowired
    protected lateinit var sql: KSqlClient
}