package com.mackay.mackfitness.services

import com.mackay.mackfitness.clients.StravaApiClient
import com.mackay.mackfitness.models.ActivitySummary
import com.mackay.mackfitness.models.SyncSettings
import com.mackay.mackfitness.repositories.ActivitySummaryRepository
import com.mackay.mackfitness.repositories.SyncSettingsRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class ActivitySummaryService(
    private val activitySummaryRepository: ActivitySummaryRepository,
    private val stravaApiClient: StravaApiClient,
    private val syncSettingsRepository: SyncSettingsRepository
)  {
    private val logger: Logger = LoggerFactory.getLogger(ActivitySummaryService::class.java)

    fun getAndUpdateMyActivitySummaries(): List<ActivitySummary> {
        updateMyActivitySummaries()
        val activitySummaries = activitySummaryRepository.getAll()
        if(activitySummaries.isEmpty()){
            logger.info("No activity summaries found in database.")
        }

        return activitySummaries
    }

    fun updateMyActivitySummaries() {
        val lastUpdatedTime = syncSettingsRepository.getLastUpdatedTime() ?: Date(0)
        val myStravaAthleteId = 26973243
        val newActivitySummaries = stravaApiClient.getActivitySummaries(myStravaAthleteId, lastUpdatedTime)
        if(newActivitySummaries.isNotEmpty()){
            logger.info("Saving ${newActivitySummaries.size} new activitySummaries...")
            activitySummaryRepository.updateMany(newActivitySummaries)
            logger.info("Saved ${newActivitySummaries.size} new activitySummaries.")

            val syncSettings = SyncSettings(lastUpdated = Date())
            logger.info("Updating syncSettings with lastUpdated: ${syncSettings.lastUpdated}...")
            syncSettingsRepository.save(syncSettings)
            logger.info("Updated syncSettings with lastUpdated: ${syncSettings.lastUpdated}.")
        }
    }

//    fun getStatsSummary(): StatsSummaryReadModel {
//        val runs = getRuns()
//        val stats = StatsSummaryReadModel()
//        var totalMeters = 0.0
//        for (run in runs) {
//            stats.totalCalories += run.totalCalories
//            stats.totalDuration += run.duration
//            totalMeters += run.totalDistance
//        }
//
//        stats.totalCaloriesFormatted = "${stats.totalCalories:n0}"
//        stats.totalKilometers = DistanceConverter.metersToKilometers(totalMeters)
//        stats.totalKilometersFormatted = "${stats.totalKilometers:n0} km"
//        stats.totalMiles = DistanceConverter.metersToMiles(totalMeters)
//        stats.totalMilesFormatted = "${stats.totalMiles:n0} mi"
//        stats.totalDurationFormatted = TimeFormatter.formatToDhm(stats.totalDuration)
//
//        return stats
//    }
}