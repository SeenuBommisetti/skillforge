package com.seenu.apps.skillforge.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    object CategoryList : Screen

    @Serializable
    data class CourseDetail(val courseId: String) : Screen

    @Serializable
    data class LessonPlayer(val courseId: String, val lessonId: String) : Screen
}
