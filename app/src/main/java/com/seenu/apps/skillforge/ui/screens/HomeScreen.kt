package com.seenu.apps.skillforge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.seenu.apps.skillforge.data.model.Category
import com.seenu.apps.skillforge.data.model.Course
import com.seenu.apps.skillforge.ui.theme.BorderColor
import com.seenu.apps.skillforge.ui.theme.IconTeal
import com.seenu.apps.skillforge.ui.theme.PrimaryTeal
import com.seenu.apps.skillforge.ui.theme.ScreenBackground
import com.seenu.apps.skillforge.ui.theme.TagBeginner
import com.seenu.apps.skillforge.ui.theme.TagIntermediate
import com.seenu.apps.skillforge.ui.theme.TextPrimary
import com.seenu.apps.skillforge.ui.theme.TextSecondary
import com.seenu.apps.skillforge.ui.viewmodel.CategoryUiState
import com.seenu.apps.skillforge.ui.viewmodel.CourseViewModel
import androidx.core.graphics.toColorInt

@Composable
fun HomeScreen(
    viewModel: CourseViewModel,
    onNavigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBackground)
    ) {
        when (val state = uiState) {
            is CategoryUiState.Loading -> {
                CircularProgressIndicator(
                    color = PrimaryTeal,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is CategoryUiState.Success -> {
                CategoryListContent(
                    categories = state.categories,
                    onNavigateToDetail = onNavigateToDetail
                )
            }
            is CategoryUiState.Error -> {
                ErrorView(
                    message = state.message,
                    onRetry = { viewModel.getCategories() },
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CategoryListContent(
    categories: List<Category>,
    onNavigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    val allCourses = remember(categories) {
        categories.flatMap { it.courses }.distinctBy { it.id }
    }

    // Filter logic
    val filteredCategories = remember(categories, searchQuery) {
        if (searchQuery.isBlank()) {
            categories
        } else {
            categories.filter { category ->
                category.name.contains(searchQuery, ignoreCase = true) ||
                        category.courses.any { course ->
                            course.title.contains(searchQuery, ignoreCase = true) ||
                                    course.instructor.name.contains(searchQuery, ignoreCase = true)
                        }
            }
        }
    }

    val filteredPopularCourses = remember(allCourses, searchQuery) {
        val filtered = if (searchQuery.isBlank()) {
            allCourses
        } else {
            allCourses.filter { course ->
                course.title.contains(searchQuery, ignoreCase = true) ||
                        course.instructor.name.contains(searchQuery, ignoreCase = true)
            }
        }
        filtered.sortedByDescending { it.studentsEnrolled }.take(5)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Header Section
        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            HeaderRow()
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Search Bar
        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Categories Section
        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            SectionHeader(title = "Categories", onSeeAllClick = {})
        }

        Spacer(modifier = Modifier.height(16.dp))

        CategoriesHorizontalList(
            categories = filteredCategories
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Popular Courses Section
        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            SectionHeader(title = "Popular courses", onSeeAllClick = {})
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
            CoursesVerticalList(
                courses = filteredPopularCourses,
                onCourseClick = onNavigateToDetail
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun HeaderRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Welcome back",
                color = TextSecondary,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Find your next skill",
                color = TextPrimary,
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Notification Bell
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, BorderColor, CircleShape)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Notifications",
                    tint = TextPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }

            // User Avatar
            AsyncImage(
                model = "https://ui-avatars.com/api/?name=Aarav+Sharma&size=150&background=2dd4bf&color=ffffff&bold=true&format=png",
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(PrimaryTeal),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = "Search courses, topics...",
                color = TextSecondary,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = TextSecondary,
                modifier = Modifier.size(24.dp)
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(1.dp, BorderColor, RoundedCornerShape(20.dp))
    )
}

@Composable
fun SectionHeader(
    title: String,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = TextPrimary,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "See all",
            color = TagBeginner,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable { onSeeAllClick() }
        )
    }
}

@Composable
fun CategoriesHorizontalList(
    categories: List<Category>,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp)) // Aligns first item with 24dp (8 + 16 gap)
            categories.forEach { category ->
                CategoryCard(category = category)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Custom Scroll Indicator Bar matching design
        CustomScrollIndicator(
            progress = if (scrollState.maxValue > 0) scrollState.value.toFloat() / scrollState.maxValue else 0f
        )
    }
}

@Composable
fun CategoryCard(
    category: Category,
    modifier: Modifier = Modifier
) {
    val categoryColor = try {
        Color(category.iconColor.toColorInt())
    } catch (_: Exception) {
        PrimaryTeal
    }

    Column(
        modifier = modifier
            .width(164.dp)
            .height(172.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White)
            .border(1.dp, BorderColor, RoundedCornerShape(24.dp))
            .padding(20.dp)
    ) {
        // Icon Container
        Box(
            modifier = Modifier
                .size(52.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(categoryColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            // Inner solid color rounded box
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(categoryColor)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = category.name,
            color = TextPrimary,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "${category.courseCount} courses",
            color = TextSecondary,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 13.sp
        )
    }
}

@Composable
fun CustomScrollIndicator(
    progress: Float,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left arrow
        Text(
            text = "◀",
            color = Color(0xFFD1D1D6),
            fontSize = 10.sp,
            modifier = Modifier.padding(end = 12.dp)
        )

        // Indicator line / track
        Box(
            modifier = Modifier
                .width(160.dp)
                .height(8.dp)
                .clip(CircleShape)
                .background(Color(0xFFE5E5EA))
        ) {
            // Slider thumb
            val thumbWidth = 60.dp
            val maxOffset = 100.dp // 160.dp - 60.dp
            val currentOffset = maxOffset * progress
            Box(
                modifier = Modifier
                    .width(thumbWidth)
                    .height(8.dp)
                    .offset(x = currentOffset)
                    .clip(CircleShape)
                    .background(Color(0xFF8E8E93))
            )
        }

        // Right arrow
        Text(
            text = "▶",
            color = Color(0xFFD1D1D6),
            fontSize = 10.sp,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun CoursesVerticalList(
    courses: List<Course>,
    onCourseClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        courses.forEach { course ->
            CourseCard(
                course = course,
                onClick = { onCourseClick(course.id) }
            )
        }
    }
}

@Composable
fun CourseCard(
    course: Course,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .border(1.dp, BorderColor, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // High-Fidelity Banner Component
        CourseThumbnail(course = course)

        Spacer(modifier = Modifier.width(16.dp))

        // Info details column
        Column(
            modifier = Modifier.weight(1f)
        ) {
            val tagColor = when {
                course.level.contains("Beginner", ignoreCase = true) -> TagBeginner
                course.level.contains("Intermediate", ignoreCase = true) -> TagIntermediate
                else -> TagBeginner
            }

            Text(
                text = course.level,
                color = tagColor,
                style = MaterialTheme.typography.labelLarge,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = course.title,
                color = TextPrimary,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = course.instructor.name,
                color = TextSecondary,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Rating & Duration side-by-side details
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Vector Star Icon
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFB800),
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = course.rating.toString(),
                    color = TextPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Custom Inline Vector Clock
                ClockIcon(
                    tint = TextSecondary,
                    modifier = Modifier.size(14.dp)
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = "${course.durationHours}h",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
fun CourseThumbnail(
    course: Course,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(112.dp)
            .height(76.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        val defaultGradient = Brush.linearGradient(
            colors = listOf(PrimaryTeal, IconTeal)
        )

        // Fallback gradient UI matching design circles and text
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(defaultGradient)
        ) {
            // Circular decorative shapes
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .offset(x = 64.dp, y = (-12).dp)
                    .background(Color.White.copy(alpha = 0.15f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .offset(x = 84.dp, y = 32.dp)
                    .background(Color.White.copy(alpha = 0.08f), CircleShape)
            )

            Text(
                text = course.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                lineHeight = 13.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 12.dp, bottom = 10.dp, end = 8.dp)
            )
        }

        // Load actual image URL if available
        AsyncImage(
            model = course.thumbnailUrl,
            contentDescription = course.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ClockIcon(
    tint: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(14.dp)
            .border(1.5.dp, tint, CircleShape)
    ) {
        // Draw clock hands
        Box(
            modifier = Modifier
                .width(1.5.dp)
                .height(5.dp)
                .background(tint)
                .align(Alignment.Center)
                .offset(y = (-2).dp)
        )
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(1.5.dp)
                .background(tint)
                .align(Alignment.Center)
                .offset(x = 1.5.dp)
        )
    }
}

@Composable
fun ErrorView(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Something went wrong",
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            color = TextSecondary,
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(PrimaryTeal)
                .clickable { onRetry() }
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Retry",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
