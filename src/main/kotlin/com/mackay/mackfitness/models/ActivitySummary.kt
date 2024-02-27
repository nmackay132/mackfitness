package com.mackay.mackfitness.models

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "activitySummaries")
data class ActivitySummary(
    @JsonProperty("id")
    val id: Long?,

    @JsonProperty("resource_state")
    val resourceState: Int,

    @JsonProperty("athlete")
    val athlete: StravaAthlete?,

    @JsonProperty("name")
    val name: String?,

    @JsonProperty("distance")
    val distance: Double,

    @JsonProperty("moving_time")
    val movingTime: Int,

    @JsonProperty("elapsed_time")
    val elapsedTime: Int,

    @JsonProperty("total_elevation_gain")
    val totalElevationGain: Double,

    @JsonProperty("type")
    val type: String?,

    @JsonProperty("sport_type")
    val sportType: String?,

    @JsonProperty("workout_type")
    val workoutType: Int?,

    @JsonProperty("start_date")
    val startDate: String?,

    @JsonProperty("start_date_local")
    val startDateLocal: String?,

    @JsonProperty("timezone")
    val timezone: String?,

    @JsonProperty("utc_offset")
    val utcOffset: Double?,

    @JsonProperty("location_city")
    val locationCity: String?,

    @JsonProperty("location_state")
    val locationState: String?,

    @JsonProperty("location_country")
    val locationCountry: String?,

    @JsonProperty("achievement_count")
    val achievementCount: Int?,

    @JsonProperty("kudos_count")
    val kudosCount: Int?,

    @JsonProperty("comment_count")
    val commentCount: Int?,

    @JsonProperty("athlete_count")
    val athleteCount: Int?,

    @JsonProperty("photo_count")
    val photoCount: Int?,

    @JsonProperty("map")
    val map: Map,

    @JsonProperty("trainer")
    val trainer: Boolean?,

    @JsonProperty("commute")
    val commute: Boolean?,

    @JsonProperty("manual")
    val manual: Boolean?,

    @JsonProperty("private")
    val private: Boolean?,

    @JsonProperty("visibility")
    val visibility: String?,

    @JsonProperty("flagged")
    val flagged: Boolean?,

    @JsonProperty("gear_id")
    val gearId: String?,

    @JsonProperty("start_latlng")
    val startLatlng: List<Double>,

    @JsonProperty("end_latlng")
    val endLatlng: List<Double>,

    @JsonProperty("average_speed")
    val averageSpeed: Double?,

    @JsonProperty("max_speed")
    val maxSpeed: Double?,

    @JsonProperty("has_heartrate")
    val hasHeartrate: Boolean?,

    @JsonProperty("heartrate_opt_out")
    val heartrateOptOut: Boolean?,

    @JsonProperty("display_hide_heartrate_option")
    val displayHideHeartrateOption: Boolean?,

    @JsonProperty("elev_high")
    val elevHigh: Double?,

    @JsonProperty("elev_low")
    val elevLow: Double?,

    @JsonProperty("upload_id")
    val uploadId: Long?,

    @JsonProperty("upload_id_str")
    val uploadIdStr: String?,

    @JsonProperty("external_id")
    val externalId: String?,

    @JsonProperty("from_accepted_tag")
    val fromAcceptedTag: Boolean?,

    @JsonProperty("pr_count")
    val prCount: Int?,

    @JsonProperty("total_photo_count")
    val totalPhotoCount: Int?,

    @JsonProperty("has_kudoed")
    val hasKudoed: Boolean?
) {
    companion object {
        private const val KmToMiles = 0.621371
    }

    val totalKilometers: Double
        get() = distance / 1000

    val totalMiles: Double
        get() = totalKilometers * KmToMiles
}

data class Athlete(
    @JsonProperty("id")
    val id: Int,

    @JsonProperty("resource_state")
    val resourceState: Int?
)

data class Map(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("summary_polyline")
    val summaryPolyline: String?,

    @JsonProperty("resource_state")
    val resourceState: Int?
)
