package com.mackay.mackfitness.controllers

import com.mackay.mackfitness.models.ActivitySummary
import com.mackay.mackfitness.services.ActivitySummaryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ActivitySummaryController(private val activitySummaryService: ActivitySummaryService) {
    private val logger: Logger = LoggerFactory.getLogger(ActivitySummaryController::class.java)

    @GetMapping("/activity-summarries")
    fun getMyActivitySummaries():List<ActivitySummary> {
        logger.info("Retrieving activity summaries.")
        return activitySummaryService.getAndUpdateMyActivitySummaries()
    }
}