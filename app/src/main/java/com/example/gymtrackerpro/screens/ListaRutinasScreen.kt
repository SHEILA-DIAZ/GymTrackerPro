package com.example.gymtrackerpro.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymtrackerpro.model.Rutina
import com.example.gymtrackerpro.viewmodel.GymViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaRutinasScreen(navController: NavController, viewModel: GymViewModel) {
    val usuario by viewModel.usuarioLogueado.collectAsState()
    val rutinas by viewModel.getRutinas(usuario?.id ?: 0).collectAsState(initial = emptyList())
    
    var routineToDelete by remember { mutableStateOf<Rutina?>(null) }
    var visible by remember { mutableStateOf(false) }

    // Colores Fitness Premium
    val darkBackground = Color(0xFF0D0D0D)
    val neonPurple = Color(0xFF7B2FF7)
    val gradientPurple = Color(0xFFA855F7)
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f)

    LaunchedEffect(Unit) {
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
                            "Mis Rutinas",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
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
                            onClick = { /* Búsqueda */ },
                            modifier = Modifier
                                .padding(8.dp)
                                .clip(CircleShape)
                                .background(glassColor)
                        ) {
                            Icon(Icons.Default.Search, contentDescription = null, tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("agregar_rutina") },
                    containerColor = Color.Transparent,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.horizontalGradient(
                                listOf(neonPurple, gradientPurple)
                            )
                        )
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White, modifier = Modifier.size(32.dp))
                }
            }
        ) { padding ->
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(800)) + slideInVertically(initialOffsetY = { 30 }),
                modifier = Modifier.padding(padding)
            ) {
                if (rutinas.isEmpty()) {
                    EmptyRutinasState(neonPurple, navController)
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
                    ) {
                        items(rutinas, key = { it.id }) { rutina ->
                            RutinaItemPremium(
                                rutina = rutina,
                                neonPurple = neonPurple,
                                glassColor = glassColor,
                                onEdit = { navController.navigate("detalle_rutina/${rutina.id}") },
                                onDelete = { routineToDelete = rutina }
                            )
                        }
                    }
                }
            }

            if (routineToDelete != null) {
                AlertDialog(
                    onDismissRequest = { routineToDelete = null },
                    containerColor = Color(0xFF1A1A1A),
                    titleContentColor = Color.White,
                    textContentColor = Color.White.copy(alpha = 0.7f),
                    title = { Text("¿Eliminar Rutina?", fontWeight = FontWeight.Bold) },
                    text = { Text("Esta acción no se puede deshacer. ¿Estás seguro?") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                routineToDelete?.let { viewModel.eliminarRutina(it) }
                                routineToDelete = null
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
                        ) {
                            Text("ELIMINAR", fontWeight = FontWeight.Bold)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { routineToDelete = null }) {
                            Text("CANCELAR", color = Color.Gray)
                        }
                    },
                    shape = RoundedCornerShape(24.dp)
                )
            }
        }
    }
}

@Composable
fun RutinaItemPremium(
    rutina: Rutina,
    neonPurple: Color,
    glassColor: Color,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .clickable { onEdit() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = glassColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = rutina.ejercicio,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Chip Grupo Muscular
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(neonPurple.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = rutina.grupoMuscular.uppercase(),
                        color = neonPurple,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.History, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${rutina.series} series x ${rutina.repeticiones} reps",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    )
                }
                
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 4.dp)) {
                    Icon(Icons.Default.MonitorWeight, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${rutina.pesoKg} kg",
                        color = neonPurple,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(Icons.Default.CalendarToday, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = rutina.fecha,
                        color = Color.White.copy(alpha = 0.5f),
                        fontSize = 13.sp
                    )
                }
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    onClick = onEdit,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.05f))
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Red.copy(alpha = 0.1f))
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red, modifier = Modifier.size(20.dp))
                }
            }
        }
    }
}

@Composable
fun EmptyRutinasState(neonPurple: Color, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(neonPurple.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.FitnessCenter,
                contentDescription = null,
                modifier = Modifier.size(60.dp),
                tint = neonPurple.copy(alpha = 0.3f)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Sin rutinas aún",
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "Tu transformación comienza con el primer registro. ¡Añade una rutina ahora!",
            color = Color.White.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            fontSize = 14.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedButton(
            onClick = { navController.navigate("agregar_rutina") },
            border = androidx.compose.foundation.BorderStroke(1.dp, neonPurple),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = neonPurple)
        ) {
            Text("AGREGAR MI PRIMERA RUTINA", fontWeight = FontWeight.Bold)
        }
    }
}
