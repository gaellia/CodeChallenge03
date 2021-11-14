package com.launchpad.codechallenge03.ui.screens.addAnimal

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun AddAnimal(
    navigateBack: () -> Unit
) {
    AddAnimalState(
        navigateBack = navigateBack
    )
}


@Composable
private fun AddAnimalState(
    navigateBack: () -> Unit,
    viewModel: AddAnimalViewModel = viewModel()
) {
    val viewState by viewModel.state.collectAsState()
    AddAnimalContent(
        viewState = viewState,
        actioner = { action ->
            when (action) {
                AddAnimalAction.NavigateBack -> navigateBack()
                else -> viewModel.submitAction(action)
            }
        }
    )
}


@Composable
private fun AddAnimalContent(
    viewState: AddAnimalViewState = AddAnimalViewState(),
    actioner: (AddAnimalAction) -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Text("Welcome to add animal")
        Button(onClick = { actioner(AddAnimalAction.NavigateBack) }) {
            Text("Back")
        }
    }
}


@Preview (showBackground = true)
@Composable
private fun AddAnimalPreview() {
    AddAnimalContent(viewState = AddAnimalViewState())

}