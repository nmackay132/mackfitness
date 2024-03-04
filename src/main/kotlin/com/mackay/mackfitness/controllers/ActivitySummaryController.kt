package com.mackay.mackfitness.controllers

import com.mackay.mackfitness.models.ActivitySummary
import com.mackay.mackfitness.models.security.ActivitySummaryResponse
import com.mackay.mackfitness.repositories.ActivitySummaryRepository
import com.mackay.mackfitness.services.ActivitySummaryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ActivitySummaryController(
    private val activitySummaryService: ActivitySummaryService,
    private val activitySummaryRepository: ActivitySummaryRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(ActivitySummaryController::class.java)

    // TODO: Rename this endpoint. Also, need to separate update and retrieval logic for activity summaries.
    @GetMapping("/activity-summarries-updated")
    fun getMyActivitySummaries():List<ActivitySummary> {
        logger.info("Retrieving activity summaries.")
        return activitySummaryService.getAndUpdateMyActivitySummaries()
    }

    @GetMapping("/activity-summarries")
    fun getAllPaginated(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(required = false) sortColumn: String?,
        @RequestParam(required = false) sortDirection: String?,
        @RequestParam(required = false) filterColumn: String?,
        @RequestParam(required = false) filterValue: String?
    ): ResponseEntity<ActivitySummaryResponse> {
        val activities = activitySummaryRepository.getAllPaginated(page, size, sortColumn, sortDirection, filterColumn, filterValue)
        val totalActivities = activitySummaryRepository.count()
        val response = ActivitySummaryResponse(activities, totalActivities)
        return ResponseEntity.ok(response)
    }
}