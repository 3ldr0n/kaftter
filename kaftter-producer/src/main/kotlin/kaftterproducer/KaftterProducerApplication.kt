package kaftterproducer

import kaftterproducer.producer.TwitterProducer

fun main() {
    val producer = TwitterProducer()
    println(
        "" +
                " _   __       __ _   _             ______              _                     \n" +
                "| | / /      / _| | | |            | ___ \\            | |                    \n" +
                "| |/ /  __ _| |_| |_| |_ ___ _ __  | |_/ / __ ___   __| |_   _  ___ ___ _ __ \n" +
                "|    \\ / _` |  _| __| __/ _ \\ '__| |  __/ '__/ _ \\ / _` | | | |/ __/ _ \\ '__|\n" +
                "| |\\  \\ (_| | | | |_| ||  __/ |    | |  | | | (_) | (_| | |_| | (_|  __/ |   \n" +
                "\\_| \\_/\\__,_|_|  \\__|\\__\\___|_|    \\_|  |_|  \\___/ \\__,_|\\__,_|\\___\\___|_|   \n" +
                "                                                                             \n" +
                "                                                                             "
    )
    producer.run()
}