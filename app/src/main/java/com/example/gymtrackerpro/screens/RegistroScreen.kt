package com.example.gymtrackerpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymtrackerpro.model.Usuario
import com.example.gymtrackerpro.viewmodel.GymViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(navController: NavController, viewModel: GymViewModel) {
    var nombreCompleto by remember { mutableStateOf("") }
    var user by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear cuenta") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombreCompleto,
                onValueChange = { nombreCompleto = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = user,
                onValueChange = { user = it },
                label = { Text("Usuario") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = edad,
                onValueChange = { if (it.all { char -> char.isDigit() }) edad = it },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (nombreCompleto.isBlank() || user.isBlank() || email.isBlank() || edad.isBlank() || password.isBlank()) {
                        // show error
                        return@Button
                    }
                    
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        // show error
                        return@Button
                    }

                    val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
                    val nuevoUsuario = Usuario(
                        nombreUsuario = user,
                        password = password,
                        nombreCompleto = nombreCompleto,
                        email = email,
                        edad = edad.toIntOrNull() ?: 0,
                        fechaRegistro = fechaActual
                    )

                    viewModel.registrarUsuario(nuevoUsuario) { error ->
                        if (error == null) {
                            navController.popBackStack()
                        } else {
                            // show error
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarme")
            }
        }
    }
}
