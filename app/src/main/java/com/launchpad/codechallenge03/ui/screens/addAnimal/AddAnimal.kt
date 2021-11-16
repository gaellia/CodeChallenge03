package com.launchpad.codechallenge03.ui.screens.addAnimal

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.relocationRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.launchpad.codechallenge03.models.Animal
import com.launchpad.codechallenge03.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
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


@ExperimentalFoundationApi
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


@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
private fun AddAnimalContent(
    viewState: AddAnimalViewState = AddAnimalViewState(),
    actioner: (AddAnimalAction) -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = OffWhite)
    ) {
        // HEADER
        Row(modifier = Modifier
            .background(color = OffWhite)
            .padding(5.dp)
            .fillMaxWidth()
            .height(50.dp),
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
                .clickable(
                    enabled = viewState.name.isNotBlank()
                ) {
                    actioner(
                        AddAnimalAction.InsertAnimal(
                            name = viewState.name,
                            type = viewState.type,
                            size = viewState.size,
                            description = viewState.description
                        )
                    )
                    actioner(AddAnimalAction.NavigateBack)
                },
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (viewState.name.isNotBlank()) ConfirmGreen else Color.Transparent
            )
        }

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            // FIELDS
            val keyboardController = LocalSoftwareKeyboardController.current

            Text(modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .fillMaxWidth(),
                text = "Name",
                style = MaterialTheme.typography.body1
            )
            TextField(modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
                value = viewState.name,
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}
                ),
                onValueChange = { newName -> actioner(AddAnimalAction.UpdateName(newName)) }
            )

            Text(modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .fillMaxWidth(),
                text = "Type",
                style = MaterialTheme.typography.body1
            )
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
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
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp)
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

            Text(modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .fillMaxWidth(),
                text = "Size",
                style = MaterialTheme.typography.body1
            )
            ExposedDropdownMenuBox(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
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
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp)
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

            Text(modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
                text = "Description",
                style = MaterialTheme.typography.body1
            )

            val bringIntoViewRequester = remember { BringIntoViewRequester() }
            val coroutineScope = rememberCoroutineScope()

            TextField(modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .heightIn(min = 150.dp, Dp.Infinity)
                .bringIntoViewRequester(bringIntoViewRequester)
                .onFocusEvent {
                    if (it.isFocused) coroutineScope.launch {
                        delay(100)
                        bringIntoViewRequester.bringIntoView()
                    }
                },
                value = viewState.description,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}
                ),
                maxLines = 15,
                onValueChange = {
                    newDesc -> actioner(AddAnimalAction.UpdateDescription(newDesc))
                }
            )
        }


    }
}


@ExperimentalFoundationApi
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Preview (showBackground = true)
@Composable
private fun AddAnimalPreview() {
    AddAnimalContent(viewState = AddAnimalViewState())

}