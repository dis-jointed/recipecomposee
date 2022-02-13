package com.example.recipecomposee.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import com.example.recipecomposee.domain.model.Recipe
import com.example.recipecomposee.util.loadPicture


@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
){
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxSize()
            .clickable(onClick = onClick),elevation = 8.dp
    ) {

        Column() {
            recipe.featuredImage?.let{
                url ->
                val image =  loadPicture(url = url).value
                image?.let {
                    img ->
                    Image(
                        bitmap = img.asImageBitmap()
                        , contentDescription = ""
                        , modifier = Modifier
                            .fillMaxWidth()
                            .requiredHeight(225.dp)
                        , contentScale = ContentScale.Crop
                    )
                }

            }

            recipe.title?.let{
                title -> Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 1.dp, start = 8.dp, end = 8.dp)
                ) {

                    Text(text = title, modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start),
                    style = MaterialTheme.typography.h3)
                Text(text = recipe.rating.toString(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.h5
                )
            }
            }
        }
    }
}