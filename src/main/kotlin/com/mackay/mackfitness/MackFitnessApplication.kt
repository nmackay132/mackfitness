package com.mackay.mackfitness

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableMongoRepositories(basePackages = ["com.mackay.mackfitness.repositories"])
class MackFitnessApplication

fun main(args: Array<String>) {
	runApplication<MackFitnessApplication>(*args)
}
