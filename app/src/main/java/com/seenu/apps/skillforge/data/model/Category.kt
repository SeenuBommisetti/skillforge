package com.seenu.apps.skillforge.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("iconColor") val iconColor: String,
    @SerialName("courseCount") val courseCount: Int,
    @SerialName("courses") val courses: List<Course>
)
