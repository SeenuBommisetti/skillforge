package com.seenu.apps.skillforge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.seenu.apps.skillforge.data.model.Course
import com.seenu.apps.skillforge.data.model.Lesson
import com.seenu.apps.skillforge.ui.theme.BorderColor
import com.seenu.apps.skillforge.ui.theme.IconTeal
import com.seenu.apps.skillforge.ui.theme.LightTealBg
import com.seenu.apps.skillforge.ui.theme.PrimaryTeal
import com.seenu.apps.skillforge.ui.theme.TagBeginner
import com.seenu.apps.skillforge.ui.theme.TextPrimary
import com.seenu.apps.skillforge.ui.theme.TextSecondary
import com.seenu.apps.skillforge.ui.viewmodel.CourseViewModel
import java.util.Locale

@Composable
fun CourseDetailScreen(
    courseId: String,
    viewModel: CourseViewModel,
    onNavigateToPlayer: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val course = remember(uiState, courseId) { viewModel.getCourse(courseId) }

    if (course == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = PrimaryTeal)
        }
        return
    }

    Box(modifier = modifier.fillMaxSize().background(Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 100.dp)
        ) {
            HeroSection(course = course, onBack = onBack)
            
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Spacer(modifier = Modifier.height(24.dp))
                
                CourseHeaderInfo(course = course)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                InstructorCard(course = course)
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = course.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextSecondary,
                    lineHeight = 22.sp
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                CourseContentHeader(course = course)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                LessonList(
                    lessons = course.lessons,
                    onLessonClick = onNavigateToPlayer
                )
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
        
        EnrollBottomBar(
            price = "Free",
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun HeroSection(
    course: Course,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
    ) {
        val gradientBrush = when (course.id) {
            "course_kotlin_101" -> Brush.linearGradient(
                colors = listOf(Color(0xFF00A490), Color(0xFF008272))
            )
            "course_compose_201" -> Brush.linearGradient(
                colors = listOf(Color(0xFF5B3FF2), Color(0xFF3F69F2))
            )
            "course_node_302" -> Brush.linearGradient(
                colors = listOf(Color(0xFF22C55E), Color(0xFF16A34A))
            )
            else -> Brush.linearGradient(
                colors = listOf(Color(0xFF0D9488), Color(0xFF0F766E))
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradientBrush)
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .offset(x = 150.dp, y = (-50).dp)
                    .background(Color.White.copy(alpha = 0.1f), CircleShape)
            )
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .offset(x = 220.dp, y = 80.dp)
                    .background(Color.White.copy(alpha = 0.05f), CircleShape)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Black.copy(alpha = 0.2f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "// ${course.tags.firstOrNull() ?: "course"}",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = course.title,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 32.sp,
                    lineHeight = 38.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    course.tags.take(3).forEach { tag ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = tag,
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }

        AsyncImage(
            model = course.thumbnailUrl,
            contentDescription = course.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().alpha(0.3f) // Lower alpha to see the gradient/text? 
            // Wait, usually the image IS the background. 
            // The screenshot shows the text ON TOP of the gradient. 
            // I'll put AsyncImage BEFORE the Column if I want it as background.
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }

            IconButton(
                onClick = { /* Bookmark */ },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Icon(
                    imageVector = Icons.Outlined.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = TextPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun CourseHeaderInfo(course: Course) {
    Column {
        Text(
            text = course.title,
            style = MaterialTheme.typography.titleLarge,
            fontSize = 24.sp,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = course.subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFB800),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = course.rating.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
            
            Text(
                text = String.format(Locale.getDefault(), "%,d", course.studentsEnrolled),
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .border(1.5.dp, TextSecondary, CircleShape)
                ) {
                    Box(
                        modifier = Modifier
                            .width(1.5.dp)
                            .height(5.dp)
                            .background(TextSecondary)
                            .align(Alignment.Center)
                            .offset(y = (-2).dp)
                    )
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(1.5.dp)
                            .background(TextSecondary)
                            .align(Alignment.Center)
                            .offset(x = 1.5.dp)
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${course.durationHours}h",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
            
            Text(
                text = course.level,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = TagBeginner
            )
        }
    }
}

@Composable
fun InstructorCard(course: Course) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderColor, RoundedCornerShape(20.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(PrimaryTeal),
            contentAlignment = Alignment.Center
        ) {
            if (course.instructor.avatarUrl.isNotEmpty()) {
                AsyncImage(
                    model = course.instructor.avatarUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = course.instructor.name.take(2).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = course.instructor.name,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )
            Text(
                text = course.instructor.title,
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
        
        Text(
            text = "Follow",
            color = TagBeginner,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.clickable { }
        )
    }
}

@Composable
fun CourseContentHeader(course: Course) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "Course content",
            style = MaterialTheme.typography.titleLarge,
            color = TextPrimary
        )
        
        val totalMinutes = course.lessons.sumOf { it.durationMinutes }
        Text(
            text = "${course.lessons.size} lessons • $totalMinutes min",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary
        )
    }
}

@Composable
fun LessonList(
    lessons: List<Lesson>,
    onLessonClick: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        lessons.forEach { lesson ->
            LessonItem(lesson = lesson) { onLessonClick(lesson.id) }
        }
    }
}

@Composable
fun LessonItem(lesson: Lesson, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(if (lesson.isFree) LightTealBg else Color(0xFFF5F5F5)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (lesson.isFree) Icons.Default.PlayArrow else Icons.Outlined.Lock,
                contentDescription = null,
                tint = if (lesson.isFree) PrimaryTeal else TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = lesson.title,
                style = MaterialTheme.typography.titleSmall,
                color = TextPrimary
            )
            Text(
                text = "${lesson.durationMinutes} min",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
                fontSize = 11.sp
            )
        }
        
        if (lesson.isFree) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(LightTealBg)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "FREE",
                    color = PrimaryTeal,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun EnrollBottomBar(
    price: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "PRICE",
                    style = MaterialTheme.typography.labelLarge,
                    color = TextSecondary,
                    fontSize = 11.sp
                )
                Text(
                    text = price,
                    color = PrimaryTeal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
            
            Button(
                onClick = { /* Enroll */ },
                modifier = Modifier
                    .height(56.dp)
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(containerColor = IconTeal),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Enroll now",
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
