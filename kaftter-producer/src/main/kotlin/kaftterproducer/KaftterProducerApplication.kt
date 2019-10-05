package kaftterproducer

import kaftterproducer.producer.TwitterProducer

fun main() {
    val producer = TwitterProducer()
    producer.run()
}