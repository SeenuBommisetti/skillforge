package com.seenu.apps.skillforge.ui.viewmodel

import com.seenu.apps.skillforge.data.model.Category

sealed interface CategoryUiState {
    object Loading : CategoryUiState
    data class Success(val categories: List<Category>) : CategoryUiState
    data class Error(val message: String) : CategoryUiState
}
