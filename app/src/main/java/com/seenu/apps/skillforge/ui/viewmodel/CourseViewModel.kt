package com.seenu.apps.skillforge.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.seenu.apps.skillforge.SkillforgeApplication
import com.seenu.apps.skillforge.data.model.Course
import com.seenu.apps.skillforge.data.model.Lesson
import com.seenu.apps.skillforge.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseViewModel(
    private val repository: CourseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            _uiState.value = CategoryUiState.Loading
            try {
                val categories = repository.getCategories()
                _uiState.value = CategoryUiState.Success(categories)
            } catch (e: Exception) {
                _uiState.value = CategoryUiState.Error(e.localizedMessage ?: "An unknown error occurred")
            }
        }
    }

    /**
     * Helper to find a specific course within the loaded categories.
     */
    fun getCourse(courseId: String): Course? {
        val state = _uiState.value
        return if (state is CategoryUiState.Success) {
            state.categories.flatMap { it.courses }.find { it.id == courseId }
        } else {
            null
        }
    }

    /**
     * Helper to find a specific lesson within a course.
     */
    fun getLesson(courseId: String, lessonId: String): Pair<Course, Lesson>? {
        val course = getCourse(courseId) ?: return null
        val lesson = course.lessons.find { it.id == lessonId } ?: return null
        return Pair(course, lesson)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as SkillforgeApplication
                val repository = application.container.courseRepository
                CourseViewModel(repository = repository)
            }
        }
    }
}
