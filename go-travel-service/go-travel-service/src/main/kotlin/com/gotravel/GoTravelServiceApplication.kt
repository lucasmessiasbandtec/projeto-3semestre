package com.gotravel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GoTravelServiceApplication

fun main(args: Array<String>) {
	runApplication<GoTravelServiceApplication>(*args)
}
