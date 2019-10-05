package kaftter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KaftterApplication

fun main(args: Array<String>) {
    runApplication<KaftterApplication>(*args)
}