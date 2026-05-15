package com.example.gymtrackerpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymtrackerpro.viewmodel.GymViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: GymViewModel) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.FitnessCenter,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "GymTracker Pro",
                fontSize = 28.sp,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(text = "Inicia sesión para continuar")
            
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.login(user, password) { success ->
                        if (success) {
                            navController.navigate("menu") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            showError = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }

            TextButton(onClick = { navController.navigate("registro") }) {
                Text("¿No tienes cuenta? Regístrate")
            }

            if (showError) {
                LaunchedEffect(Unit) {
                    snackbarHostState.showSnackbar("Credenciales incorrectas")
                    showError = false
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // We can't easily preview with a real NavController and ViewModel, 
    // but we can pass mock/dummy ones or refactor for previews.
    // For now, let's just see if it renders.
}
