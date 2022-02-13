package com.example.recipecomposee.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.recipecomposee.presentation.BaseApplication
import com.example.recipecomposee.presentation.components.*
import com.example.recipecomposee.presentation.components.util.SnackbarController
import com.example.recipecomposee.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val snackbarController = SnackbarController(lifecycleScope)

    val viewModel: RecipeListViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                /*   val isShowing = remember {
                       mutableStateOf(false)
                   }*/
                /*  val snackbarHostState = remember {
                      SnackbarHostState()
                  }
                  Column() {

                      Button(onClick = {
                          lifecycleScope.launch(){
                              snackbarHostState.showSnackbar(
                                  message = "Hey Look A snackbar!!",
                                  actionLabel = "Hide",
                                  duration = SnackbarDuration.Short
                              )
                          }

                      }) {
                          Text(text = "Show Snackbar")
                      }


                      DecoupledSnackbarDemo(snackbarHostState = snackbarHostState)
            *//*          SnackbarDemo(
                        onHideSnackbar = {
                            isShowing.value = false
                        },
                        isShowing = isShowing.value
                    )*//*
                }*/

                AppTheme(darkTheme = application.isDark.value) {
                    val recipes = viewModel.recipes.value

                    val query = viewModel.query.value

                    val selectedCategory = viewModel.selectedCategory.value

                    val loading = viewModel.loading.value

                    val page = viewModel.page.value

                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        topBar = {
                            if (selectedCategory != null) {
                                SearchAppBar(
                                    query = query,
                                    onQueryChanged = viewModel::onQueryChanged,
                                    onExecuteSearch = {
                                        if (viewModel.selectedCategory.value?.value == "Milk") {
                                            snackbarController.getScope().launch() {
                                                snackbarController.showSnackbar(
                                                    scaffoldState = scaffoldState,
                                                    message = " Invalid Category : Milk",
                                                    actionLabel = "Hide"
                                                )
                                            }
                                        } else {
                                            viewModel.onTriggerEvent(RecipeListEvent.NewSearchEvent)
                                        }
                                    }, //higher order function
                                    selectedCategory = selectedCategory,
                                    onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                    onToggleTheme = {
                                        application.toggleDarkTheme()
                                    }
                                )
                            }
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }

                    ) {

                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            onChangeRecipeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            page = page,
                            onNextPage = {
                                             viewModel.onTriggerEvent(RecipeListEvent.NextPageEvent)
                            },
                            scaffoldState = scaffoldState,
                            snackbarController = snackbarController,
                            navController = findNavController()
                        )
                    }
                    /*     Column {
                         if (selectedCategory != null) {
                             SearchAppBar(
                                 query = query,
                                 onQueryChanged = viewModel::onQueryChanged,
                                 onExecuteSearch = viewModel::newSearch, //higher order function
                                 selectedCategory = selectedCategory,
                                 onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                 onToggleTheme = {
                                     application.toggleDarkTheme()
                                 }
                             )
                         }

                        *//*  ShimmerRecipeCardItem(colors = listOf(
                             Color.LightGray.copy(alpha = 0.9f),
                             Color.LightGray.copy(alpha = 0.2f),
                             Color.LightGray.copy(alpha = 0.9f),
                         ), cardHeight = 250.dp)*//*

                    }*/

                }
            }


        }
    }

}

@Composable
fun DecoupledSnackbarDemo(
    snackbarHostState: SnackbarHostState
) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackbar = createRef()
        SnackbarHost(
            modifier = Modifier.constrainAs(snackbar) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    action = {
                        TextButton(onClick = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }) {
                            Text(
                                text = snackbarHostState.currentSnackbarData?.actionLabel ?: "",
                                style = TextStyle(Color.White)
                            )
                        }
                    }
                ) {
                    Text(text = snackbarHostState.currentSnackbarData?.message ?: "")
                }
            }
        )

    }
}

@Composable
fun SnackbarDemo(
    onHideSnackbar: () -> Unit,
    isShowing: Boolean
) {
    if (isShowing) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val snackbar = createRef()
            Snackbar(
                modifier = Modifier.constrainAs(snackbar) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                action = {
                    Text(
                        text = "Hide",
                        modifier = Modifier.clickable(
                            onClick = onHideSnackbar
                        ),
                        style = MaterialTheme.typography.h5
                    )
                }
            ) {

                Text(text = "Hey look a snackbar")
            }

        }
    }

}

@Composable
fun myDrawer() {
    Text(text = "First item")
    Text(text = "First item")
    Text(text = "First item")
    Text(text = "First item")
}

@Composable
fun MyBottomBar(
    //navController: NavController
) {
    BottomNavigation(
        elevation = 12.dp
    ) {
        BottomNavigationItem(
            selected = false,
            onClick = {
                //  navController.navigate()
            },
            icon = { Icon(Icons.Filled.BrokenImage, contentDescription = "") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Filled.Search, contentDescription = "") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Filled.AccountBalanceWallet, contentDescription = "") }
        )
    }
}

@Composable
fun GradientDemo() {
    val colors = listOf(
        Color.Blue,
        Color.Red,
        Color.Blue
    )

    val brush = linearGradient(
        colors,
        start = Offset(200f, 200f),
        end = Offset(400f, 400f)
    )

    Surface(shape = MaterialTheme.shapes.small) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = brush)
        )
    }

}