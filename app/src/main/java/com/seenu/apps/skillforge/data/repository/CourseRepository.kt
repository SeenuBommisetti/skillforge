package com.seenu.apps.skillforge.data.repository

import com.seenu.apps.skillforge.data.model.Category

interface CourseRepository {
    suspend fun getCategories(): List<Category>
}
