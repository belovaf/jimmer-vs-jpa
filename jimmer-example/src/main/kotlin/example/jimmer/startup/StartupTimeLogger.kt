package example.jimmer.startup

import org.babyfish.jimmer.sql.kt.KSqlClient
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class StartupTimeLogger : BeanPostProcessor {
    private var start: Long = 0;

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is KSqlClient) {
            start = System.currentTimeMillis()
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean is KSqlClient) {
            println("KSqlClient initialized in: ${System.currentTimeMillis() - start} ms")
        }
        return bean
    }
}