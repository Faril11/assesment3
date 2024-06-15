package org.d3if0006.mobpro1.asessmen3fazrul.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.d3if0006.mobpro1.asessmen3fazrul.R
import org.d3if0006.mobpro1.asessmen3fazrul.system.database.mainViewModel
import org.d3if0006.mobpro1.asessmen3fazrul.system.database.model.User
import org.d3if0006.mobpro1.asessmen3fazrul.system.network.OutfitsStatus
import org.d3if0006.mobpro1.asessmen3fazrul.ui.component.ListItem

@Composable
fun PublicGrid(name: String, viewModel: mainViewModel, modifier: Modifier = Modifier, user: User) {
    val status by viewModel._status.collectAsState()
    val outfits by viewModel.outfitsData.observeAsState()

    when (status) {
        OutfitsStatus.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Loading...",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        OutfitsStatus.SUCCESS -> {
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxSize()
                    .padding(4.dp),
                columns = GridCells.Fixed(2)
            ) {
                items(outfits!!) { outfit ->
                    ListItem(outfit, onDelete = { viewModel.deleteOutfits(it) }, user)
                }
            }
        }

        OutfitsStatus.FAILED -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.error))
                Button(
                    onClick = { viewModel.getAllOutfits() },
                    modifier = Modifier.padding(top = 16.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text(text = stringResource(id = R.string.try_again))
                }
            }

        }

        null -> TODO()
    }

}