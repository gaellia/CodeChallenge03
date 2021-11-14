package com.launchpad.codechallenge03.ui.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.launchpad.codechallenge03.core.getViewModelFactory
import com.launchpad.codechallenge03.ui.theme.CodeChallenge03Theme

@Composable
fun Details(
    id: String,
    navigateBack: () -> Unit
) {
    DetailsState(
        id = id,
        navigateBack = navigateBack
    )
}

@Composable
private fun DetailsState(
    id: String,
    viewModel: DetailsViewModel = viewModel(
        factory = getViewModelFactory { DetailsViewModel(id) }
    ),
    navigateBack: () -> Unit
) {
    val viewState by viewModel.state.collectAsState()

    LaunchedEffect(viewState.isDeleted){
        if(viewState.isDeleted)navigateBack()
    }

    DetailsContent(
        viewState = viewState,
        actioner = { action ->
            when (action) {
                DetailsAction.NavigateBack -> navigateBack()
                else -> viewModel.submitAction(action)
            }
        }
    )
}

@Composable
private fun DetailsContent(
    viewState: DetailsViewState = DetailsViewState(),
    actioner: (DetailsAction) -> Unit = {}
) {
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        Row(modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(modifier = Modifier
                .size(40.dp)
                .clickable { actioner(DetailsAction.NavigateBack) },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
            Text(
                text = viewState.animal?.name ?: "",
                style = MaterialTheme.typography.h5
            )
            Icon(modifier = Modifier
                .size(40.dp)
                .clickable { actioner(DetailsAction.DeleteAnimal) },
                imageVector = Icons.Default.Delete,
                contentDescription = null
            )
        }
        Text(modifier = Modifier
            .padding(5.dp),
            text = "Type: ${viewState.animal?.type?.name}"
        )
        Text(modifier = Modifier
            .padding(5.dp),
            text = "Size: ${viewState.animal?.size?.name}"
        )
        Text(modifier = Modifier
            .padding(5.dp),
            text = "Description:\n\n${viewState.animal?.description}"
        )
        Text(modifier = Modifier
            .padding(5.dp),
            text = "Encountered: ${viewState.animal?.getReadableTimeStamp()}"
        )
    }
}

@Preview
@Composable
private fun DetailsPreview() {
    CodeChallenge03Theme {
        DetailsContent(viewState = DetailsViewState())
    }
}
