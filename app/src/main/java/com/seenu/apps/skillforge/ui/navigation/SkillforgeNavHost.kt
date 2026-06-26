package com.seenu.apps.skillforge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.seenu.apps.skillforge.ui.screens.CourseDetailScreen
import com.seenu.apps.skillforge.ui.screens.HomeScreen
import com.seenu.apps.skillforge.ui.screens.LessonPlayerScreen
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
            val viewModel: CourseViewModel = viewModel(factory = CourseViewModel.Factory)
            LessonPlayerScreen(
                courseId = route.courseId,
                lessonId = route.lessonId,
                viewModel = viewModel,
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToLesson = { newLessonId ->
                    navController.navigate(Screen.LessonPlayer(route.courseId, newLessonId)) {
                        popUpTo(Screen.LessonPlayer(route.courseId, route.lessonId)) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

