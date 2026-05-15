package com.example.gymtrackerpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymtrackerpro.model.Rutina
import com.example.gymtrackerpro.viewmodel.GymViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRutinasScreen(navController: NavController, viewModel: GymViewModel) {
    val usuario by viewModel.usuarioLogueado.collectAsState()
    val rutinas by viewModel.getRutinas(usuario?.id ?: 0).collectAsState(initial = emptyList())
    
    var routineToDelete by remember { mutableStateOf<Rutina?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis rutinas") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Search */ }) {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("agregar_rutina") }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(rutinas) { rutina ->
                RutinaItem(
                    rutina = rutina,
                    onEdit = { navController.navigate("detalle_rutina/${rutina.id}") },
                    onDelete = { routineToDelete = rutina }
                )
            }
        }

        if (routineToDelete != null) {
            AlertDialog(
                onDismissRequest = { routineToDelete = null },
                title = { Text("Confirmar eliminación") },
                text = { Text("¿Estás seguro de que deseas eliminar esta rutina?") },
                confirmButton = {
                    TextButton(onClick = {
                        routineToDelete?.let { viewModel.eliminarRutina(it) }
                        routineToDelete = null
                    }) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { routineToDelete = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun RutinaItem(rutina: Rutina, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = rutina.ejercicio, style = MaterialTheme.typography.titleMedium)
                Surface(
                    color = when(rutina.grupoMuscular) {
                        "Pecho" -> Color(0xFFBBDEFB)
                        "Espalda" -> Color(0xFFC8E6C9)
                        "Pierna" -> Color(0xFFFFF9C4)
                        "Brazo" -> Color(0xFFFFE0B2)
                        else -> Color(0xFFF5F5F5)
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = rutina.grupoMuscular,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Text(
                    text = "${rutina.series} series x ${rutina.repeticiones} reps - ${rutina.pesoKg} kg - ${rutina.fecha}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Blue)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                }
            }
        }
    }
}
