package com.example.gymtrackerpro.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymtrackerpro.model.Rutina
import com.example.gymtrackerpro.viewmodel.GymViewModel
import kotlinx.coroutines.delay

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
    var visible by remember { mutableStateOf(false) }

    // Colores Fitness Premium
    val darkBackground = Color(0xFF0D0D0D)
    val neonPurple = Color(0xFF7B2FF7)
    val gradientPurple = Color(0xFFA855F7)
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f)

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
        delay(100)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        // Overlay de degradado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            neonPurple.copy(alpha = 0.1f),
                            Color.Transparent,
                            darkBackground
                        )
                    )
                )
        )

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "Editar Rutina",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(CircleShape)
                                .background(glassColor)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Atrás",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = { 
                                rutina?.let { 
                                    viewModel.eliminarRutina(it)
                                    navController.popBackStack()
                                }
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(CircleShape)
                                .background(Color.Red.copy(alpha = 0.1f))
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { padding ->
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(800)) + slideInVertically(initialOffsetY = { 20 }),
                modifier = Modifier.padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    // Card Informativa
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = neonPurple.copy(alpha = 0.05f)),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, neonPurple.copy(alpha = 0.2f))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = neonPurple)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Modificando registro: #$rutinaId",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Formulario Moderno
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        PremiumTextField(
                            value = ejercicio,
                            onValueChange = { ejercicio = it },
                            label = "Ejercicio",
                            icon = Icons.Default.FitnessCenter,
                            neonPurple = neonPurple,
                            glassColor = glassColor
                        )

                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded }
                        ) {
                            OutlinedTextField(
                                value = grupoMuscular,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Grupo Muscular", color = Color.Gray) },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(glassColor),
                                leadingIcon = { Icon(Icons.Default.Category, contentDescription = null, tint = neonPurple) },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = neonPurple,
                                    unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
                                    focusedLabelColor = neonPurple,
                                    unfocusedLabelColor = Color.Gray,
                                    cursorColor = neonPurple,
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White
                                ),
                                shape = RoundedCornerShape(16.dp)
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(Color(0xFF1A1A1A)).fillMaxWidth(0.85f)
                            ) {
                                grupos.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(selectionOption, color = Color.White) },
                                        onClick = {
                                            grupoMuscular = selectionOption
                                            expanded = false
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Default.Circle, contentDescription = null, tint = neonPurple, modifier = Modifier.size(8.dp))
                                        }
                                    )
                                }
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            PremiumTextField(
                                value = series,
                                onValueChange = { if (it.all { c -> c.isDigit() }) series = it },
                                label = "Series",
                                icon = Icons.Default.Repeat,
                                neonPurple = neonPurple,
                                glassColor = glassColor,
                                modifier = Modifier.weight(1f),
                                keyboardType = KeyboardType.Number
                            )
                            PremiumTextField(
                                value = repeticiones,
                                onValueChange = { if (it.all { c -> c.isDigit() }) repeticiones = it },
                                label = "Reps",
                                icon = Icons.Default.FlashOn,
                                neonPurple = neonPurple,
                                glassColor = glassColor,
                                modifier = Modifier.weight(1f),
                                keyboardType = KeyboardType.Number
                            )
                        }

                        PremiumTextField(
                            value = peso,
                            onValueChange = { peso = it },
                            label = "Peso (kg)",
                            icon = Icons.Default.MonitorWeight,
                            neonPurple = neonPurple,
                            glassColor = glassColor,
                            keyboardType = KeyboardType.Decimal
                        )

                        PremiumTextField(
                            value = fecha,
                            onValueChange = { fecha = it },
                            label = "Fecha",
                            icon = Icons.Default.CalendarToday,
                            neonPurple = neonPurple,
                            glassColor = glassColor,
                            trailingIcon = { Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray) }
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // Botones de Acción
                        val interactionSource = remember { MutableInteractionSource() }
                        val isPressed by interactionSource.collectIsPressedAsState()
                        val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "btnScale")

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
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .graphicsLayer(scaleX = scale, scaleY = scale)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(neonPurple, gradientPurple)
                                    )
                                ),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            contentPadding = PaddingValues(),
                            shape = RoundedCornerShape(16.dp),
                            interactionSource = interactionSource
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Update, contentDescription = null, tint = Color.White)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    "ACTUALIZAR CAMBIOS",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    letterSpacing = 1.2.sp
                                )
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                rutina?.let { 
                                    viewModel.eliminarRutina(it)
                                    navController.popBackStack()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("ELIMINAR RUTINA", fontWeight = FontWeight.Bold)
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun PremiumTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    neonPurple: Color,
    glassColor: Color,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(glassColor),
        leadingIcon = { Icon(icon, contentDescription = null, tint = neonPurple) },
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = neonPurple,
            unfocusedBorderColor = Color.White.copy(alpha = 0.1f),
            focusedLabelColor = neonPurple,
            unfocusedLabelColor = Color.Gray,
            cursorColor = neonPurple,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        singleLine = true
    )
}
