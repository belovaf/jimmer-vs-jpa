package example.jimmer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JimmerApp

fun main(args: Array<String>) {
    runApplication<JimmerApp>(args = args)
}