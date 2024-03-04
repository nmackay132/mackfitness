package com.mackay.mackfitness.controllers

import com.mackay.mackfitness.clients.StravaAuthClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController(
    private val stravaAuthClient: StravaAuthClient
    ) {
    private val logger: Logger = LoggerFactory.getLogger(HomeController::class.java)
    @GetMapping("/")
    fun index(): String {
        return "Welcome to Mackfitness!"
    }

    // TODO: Replace this. Currently only for test purposes.
    @GetMapping("/strava/authorize")
    fun authorize(): String {
        logger.info("Retrieving Strava's authorization URL.")
        return stravaAuthClient.getAuthorizationUrl()
    }
}