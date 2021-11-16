package com.launchpad.codechallenge03.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.launchpad.codechallenge03.core.getViewModelFactory
import com.launchpad.codechallenge03.models.Animal
import com.launchpad.codechallenge03.R
import com.launchpad.codechallenge03.ui.theme.*

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
        .background(
            color =
            when (viewState.animal?.type) {
                Animal.Type.AIR -> LightBlue
                Animal.Type.SEA -> LightGreen
                Animal.Type.LAND -> LightBrown
                else -> OffWhite
            }
        )
    ) {
        // HEADER
        Row(modifier = Modifier
            .background(color = OffWhite)
            .padding(5.dp)
            .fillMaxWidth()
            .height(50.dp),
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
                contentDescription = null,
                tint = DeleteRed
            )
        }

        // CONTENT
        val animalIcon: Int
        val iconColor: Color
        when (viewState.animal?.type) {
            Animal.Type.LAND -> {
                animalIcon = R.drawable.baseline_terrain_24
                iconColor = DarkBrown
            }
            Animal.Type.AIR -> {
                animalIcon = R.drawable.baseline_air_24
                iconColor = DarkBlue
            }
            Animal.Type.SEA -> {
                animalIcon = R.drawable.baseline_water_24
                iconColor = MediumGreen
            }
            else -> {
                animalIcon = R.drawable.outline_pets_24
                iconColor = TransparentBlack
            }
        }

        Icon(painterResource(id = animalIcon),
            contentDescription = null,
            tint = OffWhite,
            modifier = Modifier
                .padding(20.dp)
                .size(250.dp)
                .background(color = iconColor, shape = CircleShape)
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxSize()
            .background(
                color = OffWhite,
                shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
            )
        ) {
            Row(modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Type: ")
                        }
                        append("${viewState.animal?.type?.name}")
                    },
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Size: ")
                        }
                        append("${viewState.animal?.size?.name}")
                    },
                    modifier = Modifier.padding(5.dp)
                )
            }
            Text(modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
                text = "Description:\n",
                style = MaterialTheme.typography.h4
            )
            Text(modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .verticalScroll(rememberScrollState()),
                text = "${viewState.animal?.description}"
            )
//            Text(
//                buildAnnotatedString {
//                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
//                        append("Description:\n\n")
//                    }
//                    append("${viewState.animal?.description}")
//                },
//                modifier = Modifier
//                    .padding(horizontal = 20.dp)
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.8f)
//                    .verticalScroll(rememberScrollState())
//            )
            Row(modifier = Modifier
                .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Encountered:",
                    style = MaterialTheme.typography.h4
                )
                Text(
                    text = "${viewState.animal?.getReadableTimeStamp()}"
                )
            }

        }

    }
}

@Preview (showBackground = true)
@Composable
private fun DetailsPreview() {
    CodeChallenge03Theme {
        DetailsContent(viewState = DetailsViewState())
    }
}
