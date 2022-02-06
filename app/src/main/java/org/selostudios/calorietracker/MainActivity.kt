package org.selostudios.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import org.selostudios.calorietracker.navigation.navigate
import org.selostudios.calorietracker.ui.theme.CalorieTrackerTheme
import org.selostudios.core.navigation.Route
import org.selostudios.onboarding_presentation.activity.ActivityScreen
import org.selostudios.onboarding_presentation.age.AgeScreen
import org.selostudios.onboarding_presentation.gender.GenderScreen
import org.selostudios.onboarding_presentation.goal.GoalScreen
import org.selostudios.onboarding_presentation.height.HeightScreen
import org.selostudios.onboarding_presentation.nutrientgoal.NutrientGoalScreen
import org.selostudios.onboarding_presentation.weight.WeightScreen
import org.selostudios.onboarding_presentation.welcome.WelcomeScreen
import org.selostudios.tracker_presentation.ui.trackeroverview.TrackerOverviewScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(navController = navController, startDestination = Route.WELCOME) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                onNavigate = navController::navigate,
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                onNavigate = navController::navigate,
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                onNavigate = navController::navigate,
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                onNavigate = navController::navigate,
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.ACTIVITY) {
                            ActivityScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.GOAL) {
                            GoalScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigate = navController::navigate)
                        }
                        composable(Route.SEARCH) {

                        }
                    }
                }
            }
        }
    }
}