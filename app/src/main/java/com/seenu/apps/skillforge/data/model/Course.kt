package com.seenu.apps.skillforge.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("description") val description: String,
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("instructorName") val instructorName: String,
    @SerialName("rating") val rating: Double,
    @SerialName("difficulty") val difficulty: String,
    @SerialName("duration") val duration: String,
    @SerialName("lessons") val lessons: List<Lesson> = emptyList()
)
