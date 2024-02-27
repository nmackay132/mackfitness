package com.mackay.mackfitness.repositories

import com.mackay.mackfitness.models.ActivitySummary
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.FindAndReplaceOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository


@Repository
class ActivitySummaryRepository(@Autowired private val mongoTemplate: MongoTemplate) {

    fun getAll(): List<ActivitySummary> {
        return mongoTemplate.findAll(ActivitySummary::class.java)
    }

    fun updateMany(activitySummaryList: Iterable<ActivitySummary>) {
        val bulkOps = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, ActivitySummary::class.java)
        activitySummaryList.forEach { activitySummary ->
            val query = Query.query(Criteria.where(ActivitySummary::id.name).`is`(activitySummary.id))
            val options = FindAndReplaceOptions().upsert()
            bulkOps.replaceOne(query, activitySummary, options)
        }
        bulkOps.execute()
    }
}
