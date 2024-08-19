package example.jpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JpaApp

fun main(args: Array<String>) {
    runApplication<JpaApp>(args = args)
}