package kaftter

fun main() {
    println("Gathering twitter's data")
    val producer = TwitterProducer()
    producer.run()
}