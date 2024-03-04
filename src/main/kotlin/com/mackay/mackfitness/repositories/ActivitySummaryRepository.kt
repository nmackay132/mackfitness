package com.mackay.mackfitness.repositories

import com.mackay.mackfitness.models.ActivitySummary
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
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

    fun getAllPaginated(
        page: Int,
        size: Int,
        sortColumn: String?,
        sortDirection: String?,
        filterColumn: String?,
        filterValue: String?
    ): List<ActivitySummary> {
        // Create Pageable for pagination
        val pageable: Pageable = PageRequest.of(page, size)

        // Create Query
        val query = Query().with(pageable)

        // Apply sorting if provided
        if (sortColumn != null && sortDirection != null) {
            val direction = if (sortDirection == "asc") Sort.Direction.ASC else Sort.Direction.DESC
            query.with(Sort.by(direction, sortColumn))
        }

        // Apply filtering if provided
        if (filterColumn != null && filterValue != null) {
            query.addCriteria(Criteria.where(filterColumn).regex(filterValue, "i")) // Case-insensitive regex search
        }

        // Execute query and retrieve paginated results
        return mongoTemplate.find(query, ActivitySummary::class.java)
    }

    fun count(): Long {
        return mongoTemplate.count(Query(), ActivitySummary::class.java)
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
