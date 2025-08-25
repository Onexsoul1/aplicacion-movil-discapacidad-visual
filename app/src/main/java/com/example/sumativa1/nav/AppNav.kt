package com.example.sumativa1.nav

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sumativa1.data.AccessibilityStore
import com.example.sumativa1.model.AccessibilityPrefs
import com.example.sumativa1.ui.auth.LoginScreen
import com.example.sumativa1.ui.auth.RecoverScreen
import com.example.sumativa1.ui.auth.RegisterScreen
import com.example.sumativa1.ui.home.HomeScreen
import com.example.sumativa1.ui.onboarding.OnboardingAccessibilityScreen
import com.example.sumativa1.ui.theme.AccessibleTheme

@Composable
fun AppNav(nav: NavHostController) {
    val ctx = LocalContext.current
    val prefs by AccessibilityStore.flow(ctx).collectAsState(initial = AccessibilityPrefs())

    AccessibleTheme(prefs) {
        NavHost(navController = nav, startDestination = "onboarding") {
            composable("onboarding") { OnboardingAccessibilityScreen(nav) }
            composable("login") { LoginScreen(nav) }
            composable("register") { RegisterScreen(nav) }
            composable("recover") { RecoverScreen(nav) }
            composable("home") { HomeScreen() }
        }
    }
}
