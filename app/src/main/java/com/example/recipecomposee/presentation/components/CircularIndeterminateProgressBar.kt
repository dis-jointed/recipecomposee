package com.example.recipecomposee.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun CircularIndeterminateProgressBar(
    isDisplayed: Boolean
) {
    if(isDisplayed){
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (progressBar, text) = createRefs()
            val topGuideLine = createGuidelineFromTop(0.3f)
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,modifier = Modifier.constrainAs(progressBar){
                    top.linkTo(topGuideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Text(text = "Loading...",
            style = TextStyle(color = Color.Black, fontSize = 15.sp),modifier = Modifier.constrainAs(text){
                top.linkTo(progressBar.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                )
        }

        
    }
}

private  fun myDecoupledConstraints(verticalBias: Float): ConstraintSet{
return ConstraintSet{
    val guideline =createGuidelineFromTop(verticalBias)
    val progressBar = createRefFor("progressBar")
    val text = createRefFor("text")

    constrain(progressBar){
        top.linkTo(guideline)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }

    constrain(text){
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
}
}