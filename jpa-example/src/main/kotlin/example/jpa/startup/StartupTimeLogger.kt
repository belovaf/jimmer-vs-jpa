package example.jpa.startup

import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.stereotype.Component

@Component
class StartupTimeLogger : BeanPostProcessor {
    private var start: Long = 0;

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is LocalContainerEntityManagerFactoryBean) {
            start = System.currentTimeMillis()
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        if (bean is LocalContainerEntityManagerFactoryBean) {
            println("Hibernate initialized in: ${System.currentTimeMillis() - start} ms")
        }
        return bean
    }
}