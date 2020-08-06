package kaftter.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KaftterApiApplication

fun main(args: Array<String>) {
    runApplication<KaftterApiApplication>(*args)
}
