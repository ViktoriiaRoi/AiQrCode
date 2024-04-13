package com.example.aiqrcode.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aiqrcode.ui.screens.ResultScreen
import com.example.aiqrcode.ui.screens.SetupScreen
import kotlinx.coroutines.flow.Flow

@Composable
fun AppNavigation(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    SingleEventEffect(viewModel.sideEffects) { sideEffect ->
        when (sideEffect) {
            is SideEffect.ShowError -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.Setup.route,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = Routes.Setup.route) {
            SetupScreen(sendRequest = {
                viewModel.sendRequest()
                navController.navigate(Routes.Result.route)
            })
        }
        composable(route = Routes.Result.route) {
            ResultScreen(uiState.bitmap)
        }
    }
}

sealed class Routes(val route: String) {
    object Setup : Routes("setup")
    object Result : Routes("result")
}

@Composable
fun <T : Any> SingleEventEffect(
    sideEffects: Flow<T>,
    lifeCycleState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(sideEffects) {
        lifecycleOwner.repeatOnLifecycle(lifeCycleState) {
            sideEffects.collect(collector)
        }
    }
}
