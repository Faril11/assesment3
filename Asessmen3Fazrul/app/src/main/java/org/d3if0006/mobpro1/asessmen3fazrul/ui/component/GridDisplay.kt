package org.d3if0006.mobpro1.asessmen3fazrul.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import org.d3if0006.mobpro1.asessmen3fazrul.R
import org.d3if0006.mobpro1.asessmen3fazrul.system.database.model.Outfits
import org.d3if0006.mobpro1.asessmen3fazrul.system.database.model.User
import org.d3if0006.mobpro1.asessmen3fazrul.system.network.OutfitsAPI
import org.d3if0006.mobpro1.asessmen3fazrul.ui.widgets.DeleteDialog

@Composable
fun ListItem(outfit: Outfits, onDelete: (String) -> Unit, user: User) {
    var showDialog by remember { mutableStateOf(false) }

    DeleteDialog(
        openDialog = showDialog,
        onDismissRequest = { showDialog = false },
        onConfirmation = {
            onDelete(outfit.id)
            showDialog = false
        }
    )

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clickable { /* handle item click if necessary */ }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(OutfitsAPI.imgUrl(outfit.photoUrl))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = {
                    Icon(
                        painter = painterResource(id = R.drawable.broken_img),
                        contentDescription = "Error",
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center),
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = outfit.styleName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = OutfitsAPI.tagMaker(outfit.userName),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (user.email == outfit.email) {
                IconButton(
                    onClick = { showDialog = true },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp) // Increase size of the delete icon
                    )
                }
            }
        }
    }
}



/*@Composable
fun ListItem(outfit: Outfits, onDelete: (String) -> Unit, user: User) {
    var showDialog by remember { mutableStateOf(false) }

    DeleteDialog(
        openDialog = showDialog,
        onDismissRequest = { showDialog = false },
        onConfirmation = {
            onDelete(outfit.id)
            showDialog = false
        }
    )

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clickable { *//* handle item click if necessary *//* }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(OutfitsAPI.imgUrl(outfit.photoUrl))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                error = {
                    Icon(
                        painter = painterResource(id = R.drawable.broken_img),
                        contentDescription = "Error",
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center),
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            ) {
                Text(
                    text = outfit.styleName,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = OutfitsAPI.tagMaker(outfit.userName),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (user.email == outfit.email) {
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}*/


/*@Composable
fun ListItem(outfit: Outfits, onDelete: (String) -> Unit, user: User) {
    var showDialog by remember { mutableStateOf(false) }

    DeleteDialog(
        openDialog = showDialog,
        onDismissRequest = { showDialog = false },
        onConfirmation = {
            onDelete(outfit.id)
            showDialog = false
        }
    )

    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .clickable { *//* handle item click if necessary *//* }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box {
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(OutfitsAPI.imgUrl(outfit.photoUrl))
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    error = {
                        Icon(
                            painter = painterResource(id = R.drawable.broken_img),
                            contentDescription = "Error",
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.Center),
                            tint = MaterialTheme.colorScheme.error
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = outfit.styleName,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = OutfitsAPI.tagMaker(outfit.userName),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Text(
                    text = outfit.email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                if (user.email == outfit.email) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { showDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}*/



/*
@Composable
fun ListItem(outfits: Outfits, onDelete: (String) -> Unit, user: User) {
    var showDialog by remember { mutableStateOf(false) }
    DeleteDialog(
        openDialog = showDialog,
        onDismissRequest = { showDialog = false },
        onConfirmation = {
            onDelete(outfits.id)
            showDialog = false
        }
    )
    Box(
        modifier = Modifier
            .padding(4.dp)
            .border(1.dp, Color.Gray),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(OutfitsAPI.imgUrl(outfits.photoUrl))
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.broken_img),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(Color(red = 0f, green = 0f, blue = 0f, alpha = 0.5f))
                .padding(4.dp)
        ) {
            Text(
                text = outfits.styleName,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = OutfitsAPI.tagMaker(outfits.userName),
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = Color.White
            )
            if(user.email == outfits.email) {
                IconButton(onClick = {
                    showDialog = true
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }
    }
}*/
