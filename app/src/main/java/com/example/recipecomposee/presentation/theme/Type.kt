package com.example.recipecomposee.presentation.theme


import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipecomposee.R
import com.example.recipecomposee.ui.theme.Typography
import java.time.format.TextStyle

private val Poppins = FontFamily(
    Font(R.font.poppinslight, FontWeight.Light),
    Font(R.font.poppinsregular, FontWeight.Normal),
    Font(R.font.poppinsmedium, FontWeight.Medium),
    Font(R.font.poppinsbold, FontWeight.Bold),
)

val PoppinsTypography = Typography(
    h1 = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.W500,
        fontSize = 30.sp
    ),
    h2 = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp
    ),
    h3 = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp
    ),
    h4 = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp
    ),
    h5 = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),
    h6 = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp
    ),
    subtitle1 = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),
    subtitle2 = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp
    ),
    body1 = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontSize = 14.sp
    ),
    button = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        color = Color.White
    ),
    caption = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = androidx.compose.ui.text.TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    )

)