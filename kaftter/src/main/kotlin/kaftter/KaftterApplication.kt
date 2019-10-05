package kaftter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class KaftterApplication

fun main(args: Array<String>) {
    runApplication<KaftterApplication>(*args)
}