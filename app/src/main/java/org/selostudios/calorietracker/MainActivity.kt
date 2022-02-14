package org.selostudios.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import dagger.hilt.android.AndroidEntryPoint
import org.selostudios.calorietracker.ui.theme.CalorieTrackerTheme
import org.selostudios.core.doman.preferences.Preferences
import org.selostudios.calorietracker.navigation.Route
import org.selostudios.onboarding_presentation.activity.ActivityScreen
import org.selostudios.onboarding_presentation.age.AgeScreen
import org.selostudios.onboarding_presentation.gender.GenderScreen
import org.selostudios.onboarding_presentation.goal.GoalScreen
import org.selostudios.onboarding_presentation.height.HeightScreen
import org.selostudios.onboarding_presentation.nutrientgoal.NutrientGoalScreen
import org.selostudios.onboarding_presentation.weight.WeightScreen
import org.selostudios.onboarding_presentation.welcome.WelcomeScreen
import org.selostudios.tracker_presentation.ui.search.SearchScreen
import org.selostudios.tracker_presentation.ui.trackeroverview.TrackerOverviewScreen
import javax.inject.Inject

@ExperimentalCoilApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if (shouldShowOnboarding) {
                            Route.WELCOME
                        } else {
                            Route.TRACKER_OVERVIEW
                        }
                    ) {
                        composable(Route.WELCOME) {
                            WelcomeScreen(
                                onNextClick = { navController.navigate(Route.GENDER) }
                            )
                        }
                        composable(Route.GENDER) {
                            GenderScreen(onNextClick = { navController.navigate(Route.AGE) })
                        }
                        composable(Route.AGE) {
                            AgeScreen(
                                onNextClick = { navController.navigate(Route.HEIGHT) },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.HEIGHT) {
                            HeightScreen(
                                onNextClick = { navController.navigate(Route.WEIGHT) },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.WEIGHT) {
                            WeightScreen(
                                onNextClick = { navController.navigate(Route.ACTIVITY) },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.ACTIVITY) {
                            ActivityScreen(onNextClick = { navController.navigate(Route.GOAL) })
                        }
                        composable(Route.GOAL) {
                            GoalScreen(onNextClick = { navController.navigate(Route.NUTRIENT_GOAL) })
                        }
                        composable(Route.NUTRIENT_GOAL) {
                            NutrientGoalScreen(
                                onNextClick = { navController.navigate(Route.TRACKER_OVERVIEW) },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(onNavigateToSearch = { mealName, day, month, year ->
                                navController.navigate(
                                    Route.SEARCH + "/$mealName"
                                + "/$day"
                                + "/$month"
                                + "/$year")
                            })
                        }
                        composable(route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                        arguments = listOf(
                            navArgument("mealName") {
                                type = NavType.StringType
                            },
                            navArgument("dayOfMonth") {
                                type = NavType.IntType
                            },
                            navArgument("month") {
                                type = NavType.IntType
                            },
                            navArgument("year") {
                                type = NavType.IntType
                            }
                        )) {
                            //Values retrieved from backstack entry
                            //Can assert null if mandatory
                            val mealName = it.arguments?.getString("mealName")!!
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth")!!
                            val month = it.arguments?.getInt("month")!!
                            val year = it.arguments?.getInt("year")!!
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                })
                        }
                    }
                }
            }
        }
    }
}