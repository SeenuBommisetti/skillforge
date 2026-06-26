package com.seenu.apps.skillforge.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("durationMinutes") val durationMinutes: Int,
    @SerialName("isFree") val isFree: Boolean,
    @SerialName("videoUrl") val videoUrl: String,
    @SerialName("content") val content: String
)
