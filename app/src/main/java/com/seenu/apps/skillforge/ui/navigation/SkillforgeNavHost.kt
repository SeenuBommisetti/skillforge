package com.seenu.apps.skillforge.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.seenu.apps.skillforge.ui.screens.CourseDetailScreen
import com.seenu.apps.skillforge.ui.screens.HomeScreen
import com.seenu.apps.skillforge.ui.viewmodel.CourseViewModel

@Composable
fun SkillforgeNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.CategoryList,
        modifier = modifier
    ) {
        composable<Screen.CategoryList> {
            val viewModel: CourseViewModel = viewModel(factory = CourseViewModel.Factory)
            HomeScreen(
                viewModel = viewModel,
                onNavigateToDetail = { courseId ->
                    navController.navigate(Screen.CourseDetail(courseId))
                }
            )
        }

        composable<Screen.CourseDetail> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.CourseDetail>()
            val viewModel: CourseViewModel = viewModel(factory = CourseViewModel.Factory)
            CourseDetailScreen(
                courseId = route.courseId,
                viewModel = viewModel,
                onNavigateToPlayer = { lessonId ->
                    navController.navigate(Screen.LessonPlayer(route.courseId, lessonId))
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Screen.LessonPlayer> { backStackEntry ->
            val route = backStackEntry.toRoute<Screen.LessonPlayer>()
            LessonPlayerPlaceholder(
                courseId = route.courseId,
                lessonId = route.lessonId,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun LessonPlayerPlaceholder(
    courseId: String,
    lessonId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Lesson Player Screen (Placeholder)", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Course ID: $courseId", style = MaterialTheme.typography.bodyLarge)
        Text("Lesson ID: $lessonId", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Back to Course Detail")
        }
    }
}
