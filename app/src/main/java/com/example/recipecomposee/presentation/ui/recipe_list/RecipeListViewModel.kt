package com.example.recipecomposee.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.recipecomposee.domain.model.Recipe
import com.example.recipecomposee.repository.RecipeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Named

const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query"

class RecipeListViewModel
@ViewModelInject
constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    var categoryScrollPosition: Float = 0f

    val loading = mutableStateOf(false)

    val page = mutableStateOf(1)

    private var recipeListScrollPosition = 0

    init {
        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPAge(p)
        }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            setListScrollPosition(p)
        }
        savedStateHandle.get<FoodCategory>(STATE_KEY_PAGE)?.let { c ->
            setSelectedCategory(c)
        }

        if (recipeListScrollPosition != 0) {
            onTriggerEvent(RecipeListEvent.RestoreStateEvent)
        } else {
            onTriggerEvent(RecipeListEvent.NewSearchEvent)
        }

    }

    fun onTriggerEvent(event: RecipeListEvent) {

        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeListEvent.NewSearchEvent -> {
                        newSearch()
                    }

                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()
                    }
                    is RecipeListEvent.RestoreStateEvent -> {
                        restoreState()
                    }
                }

            } catch (e: Exception) {
                Log.e("TAG", "onTriggerEvent: Exception: ${e}, ${e.cause}")
            } finally {
                Log.d("TAG", "Launch job : finally called ")
            }
        }
    }

    private suspend fun restoreState() {
        loading.value = true
        val results: MutableList<Recipe> = mutableListOf()

        for (p in 1..page.value) {
            val result = repository.search(
                token = token,
                page = p,
                query = query.value
            )
            results.addAll(result)
            if (p == page.value) { //done
                recipes.value = results
                loading.value = false
            }

        }
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }


/*
    Append new recipes to the current list of recipes*/

    fun appendRecipes(recipe: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipe)
        this.recipes.value = current
    }

    fun incrementPage() {
        setPAge(page.value + 1)
    }

    /*
* use case #2*/
    private suspend fun nextPage() {
        //prevent duplicate events due to recompose happening too quickly
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            Log.d("TAG", "nextPage: triggerd: ${page.value}")

            /*
            * just to show pagination api is fast
            * */
            delay(1000)

            if (page.value > 1) {
                val result = repository.search(
                    token = token,
                    page = page.value,
                    query = query.value
                )
                Log.d("TAG", "nextPage: ${result}")
                appendRecipes(result)

            }
            loading.value = false
        }
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    /*
    * called when a new search is executed
    * */
    fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }

    fun clearSelectedCategory() {
        setSelectedCategory(null)
    }

    /*
    * use case #1*/
    private suspend fun newSearch() {
        loading.value = true

        resetSearchState()

        delay(400)
        val result = repository.search(
            token = token,
            page = 1,
            query = query.value,
        )
        recipes.value = result

        loading.value = false

    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    fun onChangeCategoryScrollPosition(position: Float) {
        categoryScrollPosition = position
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    private fun setPAge(page: Int) {
        this.page.value = page
        savedStateHandle.set(STATE_KEY_PAGE, page)
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, category)
    }

    private fun setQuery(query: String) {
        this.query.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }
}