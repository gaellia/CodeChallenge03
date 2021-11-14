package com.launchpad.codechallenge03.ui.screens.addAnimal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.launchpad.codechallenge03.models.Animal


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun AddAnimal(
    navigateBack: () -> Unit
) {
    AddAnimalState(
        navigateBack = navigateBack
    )
}


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
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


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
private fun AddAnimalContent(
    viewState: AddAnimalViewState = AddAnimalViewState(),
    actioner: (AddAnimalAction) -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        // HEADER
        val saveIconColor: Color = if (viewState.name.isNotBlank()) Color.Black else Color.Transparent

        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(modifier = Modifier
                .size(40.dp)
                .clickable { actioner(AddAnimalAction.NavigateBack) },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
            Text(modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
                text = "Add an Animal",
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center
            )
            Icon(modifier = Modifier
                .size(40.dp)
                .clickable {
                    actioner(AddAnimalAction.InsertAnimal(
                        name = viewState.name,
                        type = viewState.type,
                        size = viewState.size,
                        description = viewState.description))
                    actioner(AddAnimalAction.NavigateBack)
                },
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = saveIconColor
            )
        }

        // FIELDS
        val keyboardController = LocalSoftwareKeyboardController.current

        Text(modifier = Modifier.fillMaxWidth(),
            text = "Name")
        TextField(modifier = Modifier.fillMaxWidth(),
            value = viewState.name,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}
            ),
            onValueChange = { newName -> actioner(AddAnimalAction.UpdateName(newName)) }
        )

        Text(modifier = Modifier.fillMaxWidth(),
            text = "Type")
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = viewState.expandedTypeMenu,
            onExpandedChange = {
                actioner(AddAnimalAction.ToggleTypeMenu)
            }
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                value = viewState.type.name,
                onValueChange = { },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = viewState.expandedTypeMenu
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = viewState.expandedTypeMenu,
                onDismissRequest = {
                    actioner(AddAnimalAction.ToggleTypeMenu)
                }
            ) {
                enumValues<Animal.Type>().forEach { selectionOption ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            actioner(AddAnimalAction.UpdateType(selectionOption))
                            actioner(AddAnimalAction.ToggleTypeMenu)
                        }
                    ) {
                        Text(modifier = Modifier.fillMaxWidth(),
                            text = selectionOption.name)
                    }
                }
            }
        }

        Text(modifier = Modifier.fillMaxWidth(),
            text = "Size")
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = viewState.expandedSizeMenu,
            onExpandedChange = {
                actioner(AddAnimalAction.ToggleSizeMenu)
            }
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                value = viewState.size.name,
                onValueChange = { },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = viewState.expandedSizeMenu
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                modifier = Modifier.fillMaxWidth(),
                expanded = viewState.expandedSizeMenu,
                onDismissRequest = {
                    actioner(AddAnimalAction.ToggleSizeMenu)
                }
            ) {
                enumValues<Animal.Size>().forEach { selectionOption ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            actioner(AddAnimalAction.UpdateSize(selectionOption))
                            actioner(AddAnimalAction.ToggleSizeMenu)
                        }
                    ) {
                        Text(modifier = Modifier.fillMaxWidth(),
                            text = selectionOption.name)
                    }
                }
            }
        }

        Text(modifier = Modifier.fillMaxWidth(),
            text = "Description")
        TextField(modifier = Modifier.fillMaxWidth(),
            value = viewState.description,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {keyboardController?.hide()}
            ),
            maxLines = 10,
            onValueChange = {newDesc -> actioner(AddAnimalAction.UpdateDescription(newDesc))}
        )
    }
}


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Preview (showBackground = true)
@Composable
private fun AddAnimalPreview() {
    AddAnimalContent(viewState = AddAnimalViewState())

}