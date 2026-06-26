package com.seenu.apps.skillforge.di

import com.seenu.apps.skillforge.data.api.RetrofitClient
import com.seenu.apps.skillforge.data.repository.CourseRepository
import com.seenu.apps.skillforge.data.repository.CourseRepositoryImpl

interface AppContainer {
    val courseRepository: CourseRepository
}

class AppContainerImpl : AppContainer {
    override val courseRepository: CourseRepository by lazy {
        CourseRepositoryImpl(RetrofitClient.apiService)
    }
}
