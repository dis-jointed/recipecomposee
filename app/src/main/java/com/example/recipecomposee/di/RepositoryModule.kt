package com.example.recipecomposee.di

import com.example.recipecomposee.network.RecipeService
import com.example.recipecomposee.network.model.RecipeDtoMapper
import com.example.recipecomposee.repository.RecipeRepository
import com.example.recipecomposee.repository.RecipeRepository_impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository{
        return RecipeRepository_impl(
            recipeService,recipeDtoMapper
        )
    }

}