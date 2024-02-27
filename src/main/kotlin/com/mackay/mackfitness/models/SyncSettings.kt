package com.mackay.mackfitness.models

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "syncSettings")
data class SyncSettings(
    //string was ObjectId?
    @Id
    var id: String? = null,

    @JsonProperty("last_updated")
    var lastUpdated: Date = Date()
)
