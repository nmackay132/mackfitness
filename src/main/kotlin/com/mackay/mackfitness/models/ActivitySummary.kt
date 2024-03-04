package com.mackay.mackfitness.models

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.mongodb.core.mapping.Document
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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
    val averageSpeed: Double,

    @JsonProperty("max_speed")
    val maxSpeed: Double,

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
        private const val MpsToMph = 2.23694
    }

    val startDateLocalFormatted : String
        get()  {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(startDateLocal)
            val formatter = SimpleDateFormat("MMMM dd, yyyy")
            return formatter.format(date)

        }

    val totalKilometers: Double
        get() = distance / 1000

    val totalMiles: String
        get() {
            val df = DecimalFormat("#,##0.00")
            return df.format(distance / 1000 * KmToMiles)
        }

    val movingTimeFormatted: String
        get() {
            val hours = movingTime / 3600
            val minutes = (movingTime % 3600) / 60
            val seconds = movingTime % 60

            return if (hours > 0) {
                "${hours}h ${minutes}m ${seconds}s"
            } else {
                "${minutes}m ${seconds}s"
            }
        }
    val avgPace: String
        get() {
            if(distance == 0.0) {
                return "0:00"
            }

            val distanceMiles = distance / 1609.34
            val movingTimeMinutes = movingTime / 60.0
            val paceMinutesPerMile = movingTimeMinutes / distanceMiles

            // Convert pace to minutes and seconds format
            val paceMinutes = paceMinutesPerMile.toInt()
            val paceSeconds = ((paceMinutesPerMile - paceMinutes) * 60).toInt()
            val formattedPaceSeconds = String.format("%02d", paceSeconds)

            return "$paceMinutes:$formattedPaceSeconds"
        }
    val avgSpeedMph: String
        get() {
            val df = DecimalFormat("###.00")
            return df.format(averageSpeed * MpsToMph)
        }

    val elevationGainFeet: String
        get() {
            val df = DecimalFormat("###,###")
            return df.format(totalElevationGain * 3.28084)
        }
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
