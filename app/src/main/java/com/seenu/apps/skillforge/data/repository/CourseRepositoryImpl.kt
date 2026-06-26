package com.seenu.apps.skillforge.data.repository

import com.seenu.apps.skillforge.data.api.SkillforgeApiService
import com.seenu.apps.skillforge.data.model.Category
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CourseRepositoryImpl(
    private val apiService: SkillforgeApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CourseRepository {
    override suspend fun getCategories(): List<Category> = withContext(ioDispatcher) {
        apiService.getCategories()
    }
}
