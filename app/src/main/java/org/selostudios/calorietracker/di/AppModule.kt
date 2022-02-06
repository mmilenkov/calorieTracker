package org.selostudios.calorietracker.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.selostudios.core.data.preferences.DefaultPreferences
import org.selostudios.core.doman.preferences.Preferences
import org.selostudios.core.doman.usecase.FilterOutDigits
import org.selostudios.onboarding_domain.usecase.ValidateNutrientValues
import javax.inject.Singleton

//When using dynamic modules dagger hilt won't work. Use dagger 2/something else
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences {
        return DefaultPreferences(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("shared_pref", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providesFilterOutDigitsUseCase(): FilterOutDigits {
        return FilterOutDigits()
    }
}