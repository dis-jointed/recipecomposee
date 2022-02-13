package com.example.recipecomposee.repository

import com.example.recipecomposee.domain.model.Recipe
import com.example.recipecomposee.domain.util.DomainMapper
import com.example.recipecomposee.network.RecipeService
import com.example.recipecomposee.network.model.RecipeDto
import com.example.recipecomposee.network.model.RecipeDtoMapper

class RecipeRepository_impl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
): RecipeRepository {
    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(recipeService.search(token, page, query).recipes)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(recipeService.get(token, id))
    }

}