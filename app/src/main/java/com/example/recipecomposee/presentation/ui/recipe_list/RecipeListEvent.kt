package com.example.recipecomposee.presentation.ui.recipe_list

sealed class RecipeListEvent {

    object NewSearchEvent: RecipeListEvent()

    object NextPageEvent: RecipeListEvent()

    object RestoreStateEvent: RecipeListEvent()
}