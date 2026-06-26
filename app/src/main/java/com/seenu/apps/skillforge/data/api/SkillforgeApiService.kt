package com.seenu.apps.skillforge.data.api

import com.seenu.apps.skillforge.data.model.Category
import retrofit2.http.GET

interface SkillforgeApiService {
    @GET("categories.json")
    suspend fun getCategories(): List<Category>
}
