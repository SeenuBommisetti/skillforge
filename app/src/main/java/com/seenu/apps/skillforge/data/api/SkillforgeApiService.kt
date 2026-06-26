package com.seenu.apps.skillforge.data.api

import com.seenu.apps.skillforge.data.model.SkillforgeResponse
import retrofit2.http.GET

interface SkillforgeApiService {
    @GET("data.json")
    suspend fun getCategories(): SkillforgeResponse
}
