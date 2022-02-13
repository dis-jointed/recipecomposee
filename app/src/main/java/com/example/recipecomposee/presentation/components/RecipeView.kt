package com.example.recipecomposee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.recipecomposee.domain.model.Recipe
import com.example.recipecomposee.util.loadPicture

const val IMAGE_HEIGHT = 260

@Composable
fun RecipeView(
    recipe: Recipe,
) {
    val scrollState = rememberScrollState();

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        recipe.featuredImage?.let {
            val image = loadPicture(url = it).value

            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(IMAGE_HEIGHT.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            recipe.title?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = MaterialTheme.typography.h3
                    )

                    val rank = recipe.rating.toString()

                    Text(
                        text = rank,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically),
                        style = MaterialTheme.typography.h5
                    )

                }
                recipe.publisher?.let { publisher ->
                    val updated = recipe.dateUpdated
                    Text(
                        text = if (updated != null) {
                            "Updated $updated by $publisher"
                        } else {
                            "By $publisher"
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        style = MaterialTheme.typography.caption
                    )
                }

                for (ingredient in recipe.ingredients) {
                    Text(
                        text = ingredient,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}