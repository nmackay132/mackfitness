package com.mackay.mackfitness.repositories

import com.mackay.mackfitness.models.SyncSettings
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.FindAndReplaceOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.Date

@Repository
class SyncSettingsRepository(@Autowired private val mongoTemplate: MongoTemplate)  {

    fun getLastUpdatedTime(): Date? {
        val query = Query()
        query.limit(1)
        val syncSettings = mongoTemplate.find(query, SyncSettings::class.java)
        return syncSettings.firstOrNull()?.lastUpdated
    }

    // Add or replace last updated time
    fun save(syncSettings: SyncSettings) {
        val query = Query()
        query.limit(1)
        val options = FindAndReplaceOptions().upsert()
        mongoTemplate.findAndReplace(query, syncSettings, options)
    }

}
