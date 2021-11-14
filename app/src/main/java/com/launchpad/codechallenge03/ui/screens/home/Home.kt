package com.launchpad.codechallenge03.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.launchpad.codechallenge03.models.Animal
import com.launchpad.codechallenge03.ui.theme.CodeChallenge03Theme

@Composable
fun Home(
    navigateDetails: (String) -> Unit
) {
    HomeState(
        navigateDetails = navigateDetails
    )
}

@Composable
private fun HomeState(
    viewModel: HomeViewModel = viewModel(),
    navigateDetails: (String) -> Unit
) {
    val viewState by viewModel.state.collectAsState()
    viewModel.refreshList()
    HomeContent(
        viewState = viewState,
        actioner = { action ->
            when (action) {
                is HomeAction.NavigateDetails -> navigateDetails(action.id)
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
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        TextField(modifier = Modifier
            .fillMaxWidth(),
            value = viewState.query,
            onValueChange = { newQuery ->
                actioner(HomeAction.UpdateQuery(newQuery))}
        )
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = viewState.filterTypeAir,
                onCheckedChange = {actioner(HomeAction.UpdateFilterTypeAir(it))}
            )
            Text(text = "AIR")
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = viewState.filterTypeLand,
                onCheckedChange = {actioner(HomeAction.UpdateFilterTypeLand(it))}
            )
            Text(text = "LAND")
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = viewState.filterTypeSea,
                onCheckedChange = {actioner(HomeAction.UpdateFilterTypeSea(it))}
            )
            Text(text = "SEA")
            Spacer(modifier = Modifier.weight(1f))
        }
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = viewState.filterSizeSmall,
                onCheckedChange = {actioner(HomeAction.UpdateFilterSizeSmall(it))}
            )
            Text(text = "SMALL")
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = viewState.filterSizeMedium,
                onCheckedChange = {actioner(HomeAction.UpdateFilterSizeMedium(it))}
            )
            Text(text = "MEDIUM")
            Spacer(modifier = Modifier.weight(1f))
            Checkbox(
                checked = viewState.filterSizeLarge,
                onCheckedChange = {actioner(HomeAction.UpdateFilterSizeLarge(it))}
            )
            Text(text = "LARGE")
            Spacer(modifier = Modifier.weight(1f))
        }
        LazyColumn{
            items(viewState.visibleAnimals){animal ->
                Animal(animal = animal) {
                    actioner(HomeAction.NavigateDetails(animal.id))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Animal(
    animal: Animal,
    onClick: () -> Unit
) {
    Surface(modifier = Modifier
        .padding(5.dp),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = animal.name)
            Column{
                Text(text = "Type: ${animal.type.name}")
                Text(text = "Size: ${animal.size.name}")
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
