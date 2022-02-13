package com.example.recipecomposee.network.responses

import com.example.recipecomposee.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

class RecipeSearchResponse (
    @SerializedName("count")
    var count: Int,

    @SerializedName("results")
    var recipes: List<RecipeDto>
        )