package org.selostudios.onboarding_domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import org.selostudios.onboarding_domain.usecase.ValidateNutrientValues

@Module
@InstallIn(ViewModelComponent::class)
object OnboardingDomainModule {

    @Provides
    @ViewModelScoped
    fun providesValidateNutrientValuesUseCase(): ValidateNutrientValues {
        return ValidateNutrientValues()
    }
}