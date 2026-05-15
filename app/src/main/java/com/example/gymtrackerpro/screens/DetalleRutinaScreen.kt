package com.example.gymtrackerpro.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymtrackerpro.model.Rutina
import com.example.gymtrackerpro.viewmodel.GymViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleRutinaScreen(navController: NavController, viewModel: GymViewModel, rutinaId: Int) {
    var rutina by remember { mutableStateOf<Rutina?>(null) }
    
    var ejercicio by remember { mutableStateOf("") }
    var grupoMuscular by remember { mutableStateOf("") }
    var series by remember { mutableStateOf("") }
    var repeticiones by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }

    val grupos = listOf("Pecho", "Espalda", "Pierna", "Brazo", "Hombro")
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(rutinaId) {
        rutina = viewModel.buscarRutinaPorId(rutinaId)
        rutina?.let {
            ejercicio = it.ejercicio
            grupoMuscular = it.grupoMuscular
            series = it.series.toString()
            repeticiones = it.repeticiones.toString()
            peso = it.pesoKg.toString()
            fecha = it.fecha
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar rutina #$rutinaId") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        rutina?.let { 
                            viewModel.eliminarRutina(it)
                            navController.popBackStack()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Surface(
                color = Color(0xFFE1F5FE),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Modificando registro existente",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = ejercicio,
                onValueChange = { ejercicio = it },
                label = { Text("Ejercicio") },
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = grupoMuscular,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Grupo muscular") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    grupos.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                grupoMuscular = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = series,
                    onValueChange = { if (it.all { c -> c.isDigit() }) series = it },
                    label = { Text("Series") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = repeticiones,
                    onValueChange = { if (it.all { c -> c.isDigit() }) repeticiones = it },
                    label = { Text("Repeticiones") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            OutlinedTextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso (kg)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    rutina?.let {
                        val updated = it.copy(
                            ejercicio = ejercicio,
                            grupoMuscular = grupoMuscular,
                            series = series.toIntOrNull() ?: 0,
                            repeticiones = repeticiones.toIntOrNull() ?: 0,
                            pesoKg = peso.toDoubleOrNull() ?: 0.0,
                            fecha = fecha
                        )
                        viewModel.actualizarRutina(updated)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Actualizar cambios")
            }
        }
    }
}
