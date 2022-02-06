package org.selostudios.tracker_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import org.selostudios.core.doman.preferences.Preferences
import org.selostudios.tracker_domain.repository.TrackerRepository
import org.selostudios.tracker_domain.usecase.*

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTrackerUserCases(repository: TrackerRepository, preferences: Preferences): TrackerUseCases
    {
        return TrackerUseCases(
            trackFood = TrackFood(repository),
            searchForFood = SearchForFood(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences),
            getFoodsForDate = GetFoodsForDate(repository)
        )
    }
}