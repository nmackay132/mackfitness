package com.mackay.mackfitness.repositories

import com.mackay.mackfitness.models.StravaAthlete
import com.mackay.mackfitness.models.security.StravaTokenResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class StravaTokenResponseRepository(@Autowired private val mongoTemplate: MongoTemplate) {
    fun findByAthleteId(athleteId: Int): StravaTokenResponse?{
        return mongoTemplate.findOne(Query.query(Criteria.where("athlete.id").`is`(athleteId)), StravaTokenResponse::class.java)
    }

    fun findOneAndModifyByAthleteId(athleteId: Int, tokenResponse: StravaTokenResponse): StravaTokenResponse? {
        val query = Query().addCriteria(Criteria.where("athlete.id").`is`(athleteId))
        val update = Update()
            .set(StravaTokenResponse::token_type.name, tokenResponse.token_type)
            .set(StravaTokenResponse::expires_at.name, tokenResponse.expires_at)
            .set(StravaTokenResponse::expires_in.name, tokenResponse.expires_in)
            .set(StravaTokenResponse::refresh_token.name, tokenResponse.refresh_token)
            .set(StravaTokenResponse::access_token.name, tokenResponse.access_token)

        val options = FindAndModifyOptions().returnNew(true)
        return mongoTemplate.findAndModify(query, update, options, StravaTokenResponse::class.java)
    }

    fun save(tokenResponse: StravaTokenResponse) {
        mongoTemplate.save(tokenResponse)
    }
}