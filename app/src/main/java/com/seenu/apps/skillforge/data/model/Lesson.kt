package com.seenu.apps.skillforge.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("duration") val duration: String,
    @SerialName("videoUrl") val videoUrl: String? = null,
    @SerialName("description") val description: String,
    @SerialName("order") val order: Int
)
