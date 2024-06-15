package org.d3if0006.mobpro1.asessmen3fazrul

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if0006.mobpro1.asessmen3fazrul.system.database.mainViewModel
import org.d3if0006.mobpro1.asessmen3fazrul.system.database.model.User
import org.d3if0006.mobpro1.asessmen3fazrul.system.navigation.NavigationGraph
import org.d3if0006.mobpro1.asessmen3fazrul.system.network.UserDataStore
import org.d3if0006.mobpro1.asessmen3fazrul.system.network.signIn
import org.d3if0006.mobpro1.asessmen3fazrul.system.network.signOut
import org.d3if0006.mobpro1.asessmen3fazrul.system.utils.SettingsDataStore
import org.d3if0006.mobpro1.asessmen3fazrul.ui.component.getCroppedImage
import org.d3if0006.mobpro1.asessmen3fazrul.ui.theme.Asessmen3FazrulTheme
import org.d3if0006.mobpro1.asessmen3fazrul.ui.widgets.AddForm
import org.d3if0006.mobpro1.asessmen3fazrul.ui.widgets.ProfilDialog
import org.d3if0006.mobpro1.asessmen3fazrul.ui.widgets.TopAppBarWidget

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BaseApp()
        }
    }
}

@Composable
fun BaseApp(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
    systemViewModel: mainViewModel = viewModel()
) {
    val dataStore = SettingsDataStore(context)
    val userStore = UserDataStore(context)
    val appTheme by dataStore.layoutFlow.collectAsState(true)
    var showAuthDialog by remember { mutableStateOf(false) }
    val user by userStore.userFlow.collectAsState(User())

    var shownImage by rememberSaveable { mutableStateOf(false) }
    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(contract = CropImageContract()) {
        bitmap = getCroppedImage(context.contentResolver, it)
        if (bitmap != null) shownImage = true
    }

    Asessmen3FazrulTheme(darkTheme = appTheme) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            topBar = {
                TopAppBarWidget(
                    title = stringResource(id = R.string.app_name),
                    user = user,
                    appTheme = appTheme,
                    showDialog = showAuthDialog,
                    onShowDialogChange = { showAuthDialog = it },
                    onAppThemeChange = { newTheme ->
                        CoroutineScope(Dispatchers.IO).launch {
                            dataStore.saveLayout(!appTheme)
                        }
                    }
                )
            },
            bottomBar = {
                //BottomBarWidget(navController)
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        val options = CropImageContractOptions(
                            null, CropImageOptions(
                                imageSourceIncludeGallery = true,
                                imageSourceIncludeCamera = true,
                                fixAspectRatio = true
                            )
                        )
                        launcher.launch(options)
                    }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }
        ) { paddingValues ->
            Modifier.padding(paddingValues)
            //NavigationGraph(navController, apiProfile, modifier = Modifier.padding(paddingValues))
            NavigationGraph(navController, Modifier.padding(paddingValues), systemViewModel, user)

            // LaunchedEffect to handle sign-in if needed
            LaunchedEffect(showAuthDialog) {
                if (showAuthDialog && user.email.isEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        signIn(context, userStore)
                    }
                }
            }

            // Display the dialog if showDialog is true
            if (showAuthDialog && user.email.isNotEmpty()) {
                ProfilDialog(user = user, onDismissRequest = { showAuthDialog = false }) {
                    CoroutineScope(Dispatchers.IO).launch {
                        signOut(context, userStore)
                    }
                    showAuthDialog = false
                }
            }

            if (shownImage) {
                AddForm(bitmap = bitmap, onDismissRequest = { shownImage = false }, onConfirmation = { StyleName ->
                    // Do something
                    systemViewModel.addOutfits(user.email, user.name, StyleName, bitmap!!)
                    shownImage = false
                })
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Asessmen3FazrulTheme {
        BaseApp()
    }
}