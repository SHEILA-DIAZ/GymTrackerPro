package com.example.gymtrackerpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymtrackerpro.viewmodel.GymViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioScreen(navController: NavController, viewModel: GymViewModel) {
    val usuario by viewModel.usuarioLogueado.collectAsState()
    var estadisticas by remember { mutableStateOf(Pair(0, 0.0)) }

    LaunchedEffect(usuario) {
        usuario?.let {
            estadisticas = viewModel.getEstadisticas(it.id)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi perfil") },
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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier.size(100.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = usuario?.nombreCompleto?.take(2)?.uppercase() ?: "??",
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = usuario?.nombreCompleto ?: "", style = MaterialTheme.typography.headlineMedium)
            Text(text = "@${usuario?.nombreUsuario}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(32.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(
                    value = estadisticas.first.toString(),
                    label = "Rutinas",
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    value = String.format(Locale.getDefault(), "%.0f", estadisticas.second),
                    label = "kg totales",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            InfoRow(Icons.Default.Email, "Email", usuario?.email ?: "")
            InfoRow(Icons.Default.Person, "Edad", "${usuario?.edad} años")
            InfoRow(Icons.Default.DateRange, "Miembro desde", usuario?.fechaRegistro ?: "")

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = {
                    viewModel.logout()
                    navController.navigate("login") {
                        popUpTo("menu") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar sesión")
            }
        }
    }
}

@Composable
fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, style = MaterialTheme.typography.headlineSmall)
            Text(text = label, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp), tint = Color.Gray)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
