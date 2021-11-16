package com.launchpad.codechallenge03.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.launchpad.codechallenge03.R
import com.launchpad.codechallenge03.models.Animal
import com.launchpad.codechallenge03.ui.theme.*

@Composable
fun Home(
    navigateDetails: (String) -> Unit,
    navigateAddAnimal: () -> Unit
) {
    HomeState(
        navigateDetails = navigateDetails,
        navigateAddAnimal = navigateAddAnimal
    )
}

@Composable
private fun HomeState(
    viewModel: HomeViewModel = viewModel(),
    navigateDetails: (String) -> Unit,
    navigateAddAnimal: () -> Unit
) {
    val viewState by viewModel.state.collectAsState()
    viewModel.refreshList()
    HomeContent(
        viewState = viewState,
        actioner = { action ->
            when (action) {
                is HomeAction.NavigateDetails -> navigateDetails(action.id)
                HomeAction.NavigateAddAnimal -> navigateAddAnimal()
                else -> viewModel.submitAction(action)
            }
        }
    )
}

@Composable
private fun HomeContent(
    viewState: HomeViewState = HomeViewState(),
    actioner: (HomeAction) -> Unit = {}
) {
    Box(modifier = Modifier
        .background(color = OffWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val focusManager = LocalFocusManager.current

            TextField(modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black,
                    trailingIconColor = Color.Black,
                    placeholderColor = TransparentBlack
                ),
                maxLines = 1,
                placeholder = { Text(text = "Search for an animal") },
                trailingIcon = {
                    Icon(Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.clickable { focusManager.clearFocus() }
                    )
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                value = viewState.query,
                onValueChange = { newQuery ->
                    actioner(HomeAction.UpdateQuery(newQuery))
                }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewState.filterTypeAir,
                    onCheckedChange = { actioner(HomeAction.UpdateFilterTypeAir(it)) }
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_air_24),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(text = "AIR")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = viewState.filterTypeLand,
                    onCheckedChange = { actioner(HomeAction.UpdateFilterTypeLand(it)) }
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_terrain_24),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(text = "LAND")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = viewState.filterTypeSea,
                    onCheckedChange = { actioner(HomeAction.UpdateFilterTypeSea(it)) }
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_water_24),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 2.dp)
                )
                Text(text = "SEA")
                Spacer(modifier = Modifier.weight(1f))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Checkbox(
                    checked = viewState.filterSizeSmall,
                    onCheckedChange = { actioner(HomeAction.UpdateFilterSizeSmall(it)) }
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_accessibility_new_24),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(12.dp)
                )
                Text(text = "SMALL")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = viewState.filterSizeMedium,
                    onCheckedChange = { actioner(HomeAction.UpdateFilterSizeMedium(it)) }
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_accessibility_new_24),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 2.dp)
                        .size(18.dp)
                )
                Text(text = "MEDIUM")
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = viewState.filterSizeLarge,
                    onCheckedChange = { actioner(HomeAction.UpdateFilterSizeLarge(it)) }
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_accessibility_new_24),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 3.dp)
                )
                Text(text = "LARGE")
                Spacer(modifier = Modifier.weight(1f))
            }
            LazyColumn(modifier = Modifier
                .background(color = OffWhite),
                contentPadding = PaddingValues(top = 5.dp)
            ){
                items(viewState.visibleAnimals) { animal ->
                    Animal(animal = animal) {
                        actioner(HomeAction.NavigateDetails(animal.id))
                    }
                }
            }
        }
        Button( modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 15.dp, end = 15.dp),
            onClick = {actioner(HomeAction.NavigateAddAnimal)},
            shape = RoundedCornerShape(20.dp),
            elevation = ButtonDefaults.elevation(10.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = DarkGreen,
                contentColor = OffWhite
            )
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_pets_24),
                contentDescription = null
            )
            Text(modifier = Modifier
                .padding(start = 4.dp),
                text = "Add Animal"
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Animal(
    animal: Animal,
    onClick: () -> Unit
) {
    val animalIcon: Int
    val backgroundColor: Color
    val iconColor: Color
    when (animal.type) {
        Animal.Type.LAND -> {
            backgroundColor = LightBrown
            animalIcon = R.drawable.baseline_terrain_24
            iconColor = DarkBrown
        }
        Animal.Type.AIR -> {
            backgroundColor = LightBlue
            animalIcon = R.drawable.baseline_air_24
            iconColor = DarkBlue
        }
        Animal.Type.SEA -> {
            backgroundColor = LightGreen
            animalIcon = R.drawable.baseline_water_24
            iconColor = MediumGreen
        }
    }
    Surface(modifier = Modifier
        .padding(5.dp),
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = backgroundColor,
        contentColor = Color.Black
    ) {
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(75.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(painterResource(id = animalIcon),
                contentDescription = null,
                tint = OffWhite,
                modifier = Modifier
                    .padding(5.dp)
                    .size(60.dp)
                    .background(color = iconColor, shape = CircleShape)
                    .padding(5.dp)
            )
            Column(modifier = Modifier
                .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = animal.name,
                    style = TextStyle.Default.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = RichBlack,
                        fontFamily = karlaFontFamily
                    )
                )
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Type: ${animal.type.name}")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Size: ")
                    Icon(painter = painterResource(id = R.drawable.baseline_accessibility_new_24),
                        contentDescription = null,
                        modifier = Modifier.size(
                            when (animal.size) {
                                Animal.Size.SMALL -> 12.dp
                                Animal.Size.MEDIUM -> 18.dp
                                Animal.Size.LARGE -> 24.dp
                            }
                        )
                    )
                }
            }


        }


    }
}

@Preview
@Composable
private fun AnimalPreview() {
    Animal(animal = Animal(
        id = "${System.currentTimeMillis()}",
        name = "Mouse",
        description = "A small rodent that lives in open fields, it may decide to live in your home.",
        timeStamp = System.currentTimeMillis(),
        type = Animal.Type.LAND,
        size = Animal.Size.SMALL
    )){}
}

@Preview (showBackground = true)
@Composable
private fun HomePreview() {
    CodeChallenge03Theme() {
        HomeContent(viewState = HomeViewState())
    }
}
