package com.example.recipecomposee.presentation.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.recipecomposee.R
import com.example.recipecomposee.domain.model.Recipe
import com.example.recipecomposee.presentation.components.util.SnackbarController
import com.example.recipecomposee.presentation.ui.recipe_list.PAGE_SIZE
import com.example.recipecomposee.presentation.ui.recipe_list.RecipeListEvent
import kotlinx.coroutines.launch

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: (RecipeListEvent) -> Unit,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.surface)
    ) {

        LazyColumn {
            itemsIndexed(
                items = recipes
            ) { index, recipe ->
                onChangeRecipeScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                    onNextPage(RecipeListEvent.NextPageEvent)
                }
                RecipeCard(
                    recipe = recipe,
                    onClick = {
                        if (recipe.id != null ){

                            val bundle = Bundle()
                            bundle.putInt("recipeId", recipe.id)

                            navController.navigate(R.id.viewRecipe, bundle)

                        }else{
                            snackbarController.getScope().launch {
                                snackbarController.showSnackbar(
                                    scaffoldState = scaffoldState,
                                    message = "Recipe Error",
                                    actionLabel = "Ok"
                                )
                            }
                        }
                    }
                )
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = loading)

        DefaultSnackbar(
            snackbarHostState = scaffoldState.snackbarHostState,
            onDismiss = {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
}