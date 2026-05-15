package com.example.gymtrackerpro.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymtrackerpro.viewmodel.GymViewModel
import kotlinx.coroutines.delay
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilUsuarioScreen(navController: NavController, viewModel: GymViewModel) {
    val usuario by viewModel.usuarioLogueado.collectAsState()
    var estadisticas by remember { mutableStateOf(Pair(0, 0.0)) }
    var visible by remember { mutableStateOf(false) }

    // Colores Fitness Premium
    val darkBackground = Color(0xFF0D0D0D)
    val neonPurple = Color(0xFF7B2FF7)
    val glassColor = Color(0xFFFFFFFF).copy(alpha = 0.05f)

    LaunchedEffect(usuario) {
        usuario?.let {
            estadisticas = viewModel.getEstadisticas(it.id)
        }
        delay(100)
        visible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
    ) {
        // Fondo con degradado sutil
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
                            "Perfil",
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        ) { padding ->
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(tween(800)) + slideInVertically(initialOffsetY = { 30 }),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    // Header de Perfil
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(neonPurple.copy(alpha = 0.1f))
                            .border(2.dp, neonPurple, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = usuario?.nombreCompleto?.take(1)?.uppercase() ?: "G",
                            color = Color.White,
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = usuario?.nombreCompleto ?: "Atleta",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "@${usuario?.nombreUsuario}",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Frase motivacional
                    Text(
                        text = "\"Cada repetición cuenta. Sigue superando tus límites.\"",
                        color = neonPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Estadísticas Premium
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        StatCardPremium(
                            value = estadisticas.first.toString(),
                            label = "Rutinas",
                            icon = Icons.Default.FitnessCenter,
                            accentColor = neonPurple,
                            glassColor = glassColor,
                            modifier = Modifier.weight(1f)
                        )
                        StatCardPremium(
                            value = String.format(Locale.getDefault(), "%.0f", estadisticas.second),
                            label = "kg totales",
                            icon = Icons.Default.MonitorWeight,
                            accentColor = neonPurple,
                            glassColor = glassColor,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Información del Usuario
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = glassColor),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            InfoRowPremium(Icons.Default.Email, "Email", usuario?.email ?: "", neonPurple)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.White.copy(alpha = 0.05f))
                            InfoRowPremium(Icons.Default.Person, "Edad", "${usuario?.edad} años", neonPurple)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.White.copy(alpha = 0.05f))
                            InfoRowPremium(Icons.Default.DateRange, "Miembro desde", usuario?.fechaRegistro ?: "", neonPurple)
                        }
                    }

                    Spacer(modifier = Modifier.height(48.dp))

                    // Botón Cerrar Sesión
                    Button(
                        onClick = {
                            viewModel.logout()
                            navController.navigate("login") {
                                popUpTo("menu") { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.15f)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null, tint = Color.Red)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "CERRAR SESIÓN",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                letterSpacing = 1.2.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }
        }
    }
}

@Composable
fun StatCardPremium(
    value: String,
    label: String,
    icon: ImageVector,
    accentColor: Color,
    glassColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(110.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = glassColor),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = label, color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun InfoRowPremium(icon: ImageVector, label: String, value: String, accentColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(accentColor.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp), tint = accentColor)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, color = Color.Gray, fontSize = 12.sp)
            Text(text = value, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
        }
    }
}
