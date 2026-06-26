package com.seenu.apps.skillforge.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    @SerialName("id") val id: String,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("thumbnailUrl") val thumbnailUrl: String,
    @SerialName("level") val level: String,
    @SerialName("durationHours") val durationHours: Double,
    @SerialName("rating") val rating: Double,
    @SerialName("studentsEnrolled") val studentsEnrolled: Int,
    @SerialName("language") val language: String,
    @SerialName("lastUpdated") val lastUpdated: String,
    @SerialName("tags") val tags: List<String>,
    @SerialName("instructor") val instructor: Instructor,
    @SerialName("description") val description: String,
    @SerialName("lessons") val lessons: List<Lesson>
)

@Serializable
data class Instructor(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("title") val title: String,
    @SerialName("avatarUrl") val avatarUrl: String,
    @SerialName("bio") val bio: String
)
